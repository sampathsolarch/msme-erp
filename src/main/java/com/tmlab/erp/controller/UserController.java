package com.tmlab.erp.controller;

import static com.tmlab.erp.utils.ResponseJsonUtil.returnJson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tmlab.erp.constants.ExceptionConstants;
import com.tmlab.erp.datasource.entities.User;
import com.tmlab.erp.datasource.entities.UserEx;
import com.tmlab.erp.exception.BusinessParamCheckingException;
import com.tmlab.erp.service.LogService;
import com.tmlab.erp.service.UserService;
import com.tmlab.erp.service.redis.RedisService;
import com.tmlab.erp.utils.BaseResponseInfo;
import com.tmlab.erp.utils.ErpInfo;
import com.tmlab.erp.utils.ExceptionCodeConstants;
import com.tmlab.erp.utils.RandImageUtil;
import com.tmlab.erp.utils.Tools;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author ji_sheng_hua 华夏erp
 */
@RestController
@RequestMapping(value = "/user")
@Api(tags = { "用户管理" })
public class UserController {
	private Logger logger = LoggerFactory.getLogger(UserController.class);

	@Value("${manage.roleId}")
	private Integer manageRoleId;

	@Value("${demonstrate.open}")
	private boolean demonstrateOpen;

	@Resource
	private UserService userService;

	@Resource
	private LogService logService;

	@Resource
	private RedisService redisService;

	private static final String TEST_USER = "tmlab";
	private static String SUCCESS = "SUCCESS";
	private static String ERROR = "FAILURE";

	@PostMapping(value = "/login")
	@ApiOperation(value = "login")
	public BaseResponseInfo login(@RequestBody User userParam, HttpServletRequest request) throws Exception {
		logger.info("============User login login method call starts =============");
		String msgTip = "";
		User user = null;
		BaseResponseInfo res = new BaseResponseInfo();
		try {
			String loginName = userParam.getLoginName().trim();
			String password = userParam.getPassword().trim();

			Object userId = redisService.getObjectFromSessionByKey(request, "userId");
			if (userId != null) {
				logger.info("====The user has logged in, the login method call ends====");
				msgTip = "user already login";
			}

			int userStatus = -1;
			try {
				redisService.deleteObjectBySession(request, "userId");
				userStatus = userService.validateUser(loginName, password);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(
						">>>>>>>>>>>>>User " + loginName + " login login method access service layer exception====", e);
				msgTip = "access service exception";
			}

			String token = UUID.randomUUID().toString().replaceAll("-", "") + "";
			switch (userStatus) {
			case ExceptionCodeConstants.UserExceptionCode.USER_NOT_EXIST:
				msgTip = "user is not exist";
				break;
			case ExceptionCodeConstants.UserExceptionCode.USER_PASSWORD_ERROR:
				msgTip = "user password error";
				break;
			case ExceptionCodeConstants.UserExceptionCode.BLACK_USER:
				msgTip = "user is black";
				break;
			case ExceptionCodeConstants.UserExceptionCode.USER_ACCESS_EXCEPTION:
				msgTip = "access service error";
				break;
			case ExceptionCodeConstants.UserExceptionCode.BLACK_TENANT:
				msgTip = "tenant is black";
				break;
			case ExceptionCodeConstants.UserExceptionCode.EXPIRE_TENANT:
				msgTip = "tenant is expire";
				break;
			case ExceptionCodeConstants.UserExceptionCode.USER_CONDITION_FIT:
				msgTip = "user can login";

				user = userService.getUserByLoginName(loginName);
				if (user.getTenantId() != null) {
					token = token + "_" + user.getTenantId();
				}
				redisService.storageObjectBySession(token, "userId", user.getId());

			}

			Map<String, Object> data = new HashMap<String, Object>();
			data.put("msgTip", msgTip);
			res.code = 200;
			res.data = data;
			logger.info("==============User login login method call ends ==============");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			res.code = 500;
			res.data = "User login failed";
		}
		return res;
	}

	@GetMapping(value = "/getUserSession")
	@ApiOperation(value = "Get user information")
	public BaseResponseInfo getSessionUser(HttpServletRequest request) throws Exception {
		BaseResponseInfo res = new BaseResponseInfo();
		try {
			Map<String, Object> data = new HashMap<>();
			Long userId = Long.parseLong(redisService.getObjectFromSessionByKey(request, "userId").toString());
			User user = userService.getUser(userId);
			user.setPassword(null);
			data.put("user", user);
			res.code = 200;
			res.data = data;
		} catch (Exception e) {
			e.printStackTrace();
			res.code = 500;
			res.data = "Failed to get session";
		}
		return res;
	}

	@GetMapping(value = "/logout")
	@ApiOperation(value = "exit")
	public BaseResponseInfo logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
		BaseResponseInfo res = new BaseResponseInfo();
		try {
			redisService.deleteObjectBySession(request, "userId");
		} catch (Exception e) {
			e.printStackTrace();
			res.code = 500;
			res.data = "Exit failed";
		}
		return res;
	}

	@PostMapping(value = "/resetPwd")
	@ApiOperation(value = "Reset Password")
	public String resetPwd(@RequestBody JSONObject jsonObject, HttpServletRequest request) throws Exception {
		Map<String, Object> objectMap = new HashMap<>();
		Long id = jsonObject.getLong("id");
		String password = "123456";
		String md5Pwd = Tools.md5Encryp(password);
		int update = userService.resetPwd(md5Pwd, id);
		if (update > 0) {
			return returnJson(objectMap, SUCCESS, ErpInfo.OK.code);
		} else {
			return returnJson(objectMap, ERROR, ErpInfo.ERROR.code);
		}
	}

	@PutMapping(value = "/updatePwd")
	@ApiOperation(value = "Update Password")
	public String updatePwd(@RequestBody JSONObject jsonObject, HttpServletRequest request) throws Exception {
		Integer flag = 0;
		Map<String, Object> objectMap = new HashMap<String, Object>();
		try {
			String info = "";
			Long userId = jsonObject.getLong("userId");
			String oldpwd = jsonObject.getString("oldpassword");
			String password = jsonObject.getString("password");
			User user = userService.getUser(userId);
			// The password must be the same as the original password to update the password
			if (demonstrateOpen && user.getLoginName().equals(TEST_USER)) {
				flag = 3; // jsh user cannot change the password
				info = "jsh user cannot change password";
			} else if (oldpwd.equalsIgnoreCase(user.getPassword())) {
				user.setPassword(password);
				flag = userService.updateUserByObj(user); // 1-success
				info = "Modified successfully";
			} else {
				flag = 2; // original password input error
				info = "Original password entered incorrectly";
			}
			objectMap.put("status", flag);
			if (flag > 0) {
				return returnJson(objectMap, info, ErpInfo.OK.code);
			} else {
				return returnJson(objectMap, ERROR, ErpInfo.ERROR.code);
			}
		} catch (Exception e) {
			logger.error(">>>>>>>>>>>>>modify the user ID to: " + jsonObject.getLong("userId")
					+ "password information failed", e);
			flag = 3;
			objectMap.put("status", flag);
			return returnJson(objectMap, ERROR, ErpInfo.ERROR.code);
		}
	}

	/**
	 * 获取全部用户数据列表
	 * 
	 * @param request
	 * @return
	 */
	@GetMapping(value = "/getAllList")
	@ApiOperation(value = "Get a list of all user data")
	public BaseResponseInfo getAllList(HttpServletRequest request) throws Exception {
		BaseResponseInfo res = new BaseResponseInfo();
		try {
			Map<String, Object> data = new HashMap<String, Object>();
			List<User> dataList = userService.getUser();
			if (dataList != null) {
				data.put("userList", dataList);
			}
			res.code = 200;
			res.data = data;
		} catch (Exception e) {
			e.printStackTrace();
			res.code = 500;
			res.data = "Failed to get";
		}
		return res;
	}

	/**
	 * User list for user drop-down box
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value = "/getUserList")
	@ApiOperation(value = "User List")
	public JSONArray getUserList(HttpServletRequest request) throws Exception {
		JSONArray dataArray = new JSONArray();
		try {
			List<User> dataList = userService.getUser();
			if (null != dataList) {
				for (User user : dataList) {
					JSONObject item = new JSONObject();
					item.put("id", user.getId());
					item.put("userName", user.getUsername());
					dataArray.add(item);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dataArray;
	}

	/**
	 * create by: cjl description: New users and organizations and user
	 * relationships create time: 2019/3/8 16:06
	 * 
	 * @Param: beanJson
	 * @return java.lang.Object
	 */
	@PostMapping("/addUser")
	@ApiOperation(value = "Add User")
	@ResponseBody
	public Object addUser(@RequestBody JSONObject obj, HttpServletRequest request) throws Exception {
		JSONObject result = ExceptionConstants.standardSuccess();
		Long userNumLimit = Long.parseLong(redisService.getObjectFromSessionByKey(request, "userNumLimit").toString());
		Long count = userService.countUser(null, null);
		if (count >= userNumLimit) {
			throw new BusinessParamCheckingException(ExceptionConstants.USER_OVER_LIMIT_FAILED_CODE,
					ExceptionConstants.USER_OVER_LIMIT_FAILED_MSG);
		} else {
			UserEx ue = JSONObject.parseObject(obj.toJSONString(), UserEx.class);
			userService.addUserAndOrgUserRel(ue, request);
		}
		return result;
	}

	/**
	 * create by: cjl description: Modify user and organization and user
	 * relationship create time: 2019/3/8 16:06
	 * 
	 * @Param: beanJson
	 * @return java.lang.Object
	 */
	@PutMapping("/updateUser")
	@ApiOperation(value = "Modify user")
	@ResponseBody
	public Object updateUser(@RequestBody JSONObject obj, HttpServletRequest request) throws Exception {
		JSONObject result = ExceptionConstants.standardSuccess();
		UserEx ue = JSONObject.parseObject(obj.toJSONString(), UserEx.class);
		userService.updateUserAndOrgUserRel(ue, request);
		return result;
	}

	/**
	 * registered user
	 * 
	 * @param ue
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value = "/registerUser")
	@ApiOperation(value = "registered user")
	public Object registerUser(@RequestBody UserEx ue, HttpServletRequest request) throws Exception {
		JSONObject result = ExceptionConstants.standardSuccess();
		ue.setUsername(ue.getLoginName());
		userService.checkUserNameAndLoginName(ue); // Check user name and login name
		ue = userService.registerUser(ue, manageRoleId, request);
		return result;
	}

	/**
	 * Get the role type of the current user
	 * 
	 * @param request
	 * @return
	 */
	@GetMapping("/getRoleTypeByCurrentUser")
	@ApiOperation(value = "Get the current user's role type")
	public BaseResponseInfo getRoleTypeByCurrentUser(HttpServletRequest request) {
		BaseResponseInfo res = new BaseResponseInfo();
		try {
			Map<String, Object> data = new HashMap<String, Object>();
			String roleType = redisService.getObjectFromSessionByKey(request, "roleType").toString();
			data.put("roleType", roleType);
			res.code = 200;
			res.data = data;
		} catch (Exception e) {
			e.printStackTrace();
			res.code = 500;
			res.data = "Failed to get";
		}
		return res;
	}

	/**
	 * Get random check code
	 * 
	 * @param response
	 * @param key
	 * @return
	 */
	@GetMapping(value = "/randomImage/{key}")
	@ApiOperation(value = "Get random check code")
	public BaseResponseInfo randomImage(HttpServletResponse response, @PathVariable String key) {
		BaseResponseInfo res = new BaseResponseInfo();
		try {
			Map<String, Object> data = new HashMap<>();
			String codeNum = Tools.getCharAndNum(4);
			String base64 = RandImageUtil.generate(codeNum);
			data.put("codeNum", codeNum);
			data.put("base64", base64);
			res.code = 200;
			res.data = data;
		} catch (Exception e) {
			e.printStackTrace();
			res.code = 500;
			res.data = "Failed to get";
		}
		return res;
	}

	/**
	 * Batch setting status - enable or disable
	 * 
	 * @param jsonObject
	 * @param request
	 * @return
	 */
	@PostMapping(value = "/batchSetStatus")
	@ApiOperation(value = "Batch set status")
	public String batchSetStatus(@RequestBody JSONObject jsonObject, HttpServletRequest request) throws Exception {
		Byte status = jsonObject.getByte("status");
		String ids = jsonObject.getString("ids");
		Map<String, Object> objectMap = new HashMap<>();
		int res = userService.batchSetStatus(status, ids);
		if (res > 0) {
			return returnJson(objectMap, ErpInfo.OK.name, ErpInfo.OK.code);
		} else {
			return returnJson(objectMap, ErpInfo.ERROR.name, ErpInfo.ERROR.code);
		}
	}

	/**
	 * Get the number of users and tenant information of the current user
	 * 
	 * @param request
	 * @return
	 */
	@GetMapping(value = "/infoWithTenant")
	@ApiOperation(value = "Get the number of users and tenant information of the current user")
	public BaseResponseInfo randomImage(HttpServletRequest request) {
		BaseResponseInfo res = new BaseResponseInfo();
		try {
			Map<String, Object> data = new HashMap<>();
					
			// Get the current number of users
			Long userCurrentNum = userService.countUser(null, null);

			data.put("userCurrentNum", userCurrentNum);
			res.code = 200;
			res.data = data;
		} catch (Exception e) {
			e.printStackTrace();
			res.code = 500;
			res.data = "Failed to get";
		}
		return res;
	}
}
