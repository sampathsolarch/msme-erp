package com.tmlab.erp.service;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tmlab.erp.constants.BusinessConstants;
import com.tmlab.erp.constants.ExceptionConstants;
import com.tmlab.erp.datasource.entities.User;
import com.tmlab.erp.datasource.entities.UserBusiness;
import com.tmlab.erp.datasource.entities.UserEx;
import com.tmlab.erp.datasource.entities.UserExample;
import com.tmlab.erp.datasource.mappers.UserMapper;
import com.tmlab.erp.datasource.mappers.UserMapperEx;
import com.tmlab.erp.exception.BusinessRunTimeException;
import com.tmlab.erp.exception.TMException;
import com.tmlab.erp.service.redis.RedisService;
import com.tmlab.erp.utils.ExceptionCodeConstants;
import com.tmlab.erp.utils.StringUtil;
import com.tmlab.erp.utils.Tools;

@Service
public class UserService {
	private Logger logger = LoggerFactory.getLogger(UserService.class);

	private static final String TEST_USER = "jsh";

	@Value("${demonstrate.open}")
	private boolean demonstrateOpen;

	@Resource
	private UserMapper userMapper;

	@Resource
	private UserMapperEx userMapperEx;

	@Resource
	private LogService logService;
	@Resource
	private UserService userService;

	@Resource
	private UserBusinessService userBusinessService;

	@Resource
	private RedisService redisService;

	public User getUser(long id) throws Exception {
		User result = null;
		try {
			result = userMapper.selectByPrimaryKey(id);
		} catch (Exception e) {
			TMException.readFail(logger, e);
		}
		return result;
	}

	public List<User> getUserListByIds(String ids) throws Exception {
		List<Long> idList = StringUtil.strToLongList(ids);
		List<User> list = new ArrayList<>();
		try {
			UserExample example = new UserExample();
			example.createCriteria().andIdIn(idList);
			list = userMapper.selectByExample(example);
		} catch (Exception e) {
			TMException.readFail(logger, e);
		}
		return list;
	}

	public List<User> getUser() throws Exception {
		UserExample example = new UserExample();
		example.createCriteria().andStatusEqualTo(BusinessConstants.USER_STATUS_NORMAL);
		List<User> list = null;
		try {
			list = userMapper.selectByExample(example);
		} catch (Exception e) {
			TMException.readFail(logger, e);
		}
		return list;
	}

	public List<UserEx> select(String userName, String loginName, int offset, int rows) throws Exception {
		List<UserEx> list = null;
		try {
			list = userMapperEx.selectByConditionUser(userName, loginName, offset, rows);
			for (UserEx ue : list) {
				String userType = "";
				if (demonstrateOpen && TEST_USER.equals(ue.getLoginName())) {
					userType = "Demo User";
				} else {
					if (ue.getId().equals(ue.getTenantId())) {
						userType = "tenant";
					} else if (ue.getTenantId() == null) {
						userType = "Superpipe";
					} else {
						userType = "Normal";
					}
				}
				ue.setUserType(userType);
			}
		} catch (Exception e) {
			TMException.readFail(logger, e);
		}
		return list;
	}

	public Long countUser(String userName, String loginName) throws Exception {
		Long result = null;
		try {
			result = userMapperEx.countsByUser(userName, loginName);
		} catch (Exception e) {
			TMException.readFail(logger, e);
		}
		return result;
	}

	
	@Transactional(value = "transactionManager", rollbackFor = Exception.class)
	public int insertUser(JSONObject obj, HttpServletRequest request) throws Exception {
		User user = JSONObject.parseObject(obj.toJSONString(), User.class);
		String password = "123456";
		// Because the password is encrypted with MD5, the password needs to be
		// converted
		try {
			password = Tools.md5Encryp(password);
			user.setPassword(password);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			logger.error(">>>>>>>>>>>>>>Error converting MD5 string: " + e.getMessage());
		}
		int result = 0;
		try {
			result = userMapper.insertSelective(user);
			logService.insertLog("User",
					new StringBuffer(BusinessConstants.LOG_OPERATION_TYPE_ADD).append(user.getLoginName()).toString(),
					request);
		} catch (Exception e) {
			TMException.writeFail(logger, e);
		}
		return result;
	}

	
	@Transactional(value = "transactionManager", rollbackFor = Exception.class)
	public int updateUser(JSONObject obj, HttpServletRequest request) throws Exception {
		User user = JSONObject.parseObject(obj.toJSONString(), User.class);
		int result = 0;
		try {
			result = userMapper.updateByPrimaryKeySelective(user);
			logService.insertLog("User",
					new StringBuffer(BusinessConstants.LOG_OPERATION_TYPE_EDIT).append(user.getLoginName()).toString(),
					request);
		} catch (Exception e) {
			TMException.writeFail(logger, e);
		}
		return result;
	}

	
	@Transactional(value = "transactionManager", rollbackFor = Exception.class)
	public int updateUserByObj(User user) throws Exception {
		logService.insertLog("User",
				new StringBuffer(BusinessConstants.LOG_OPERATION_TYPE_EDIT).append(user.getId()).toString(),
				((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
		int result = 0;
		try {
			result = userMapper.updateByPrimaryKeySelective(user);
		} catch (Exception e) {
			TMException.writeFail(logger, e);
		}
		return result;
	}

	
	@Transactional(value = "transactionManager", rollbackFor = Exception.class)
	public int resetPwd(String md5Pwd, Long id) throws Exception {
		int result = 0;
		logService.insertLog("User", new StringBuffer(BusinessConstants.LOG_OPERATION_TYPE_EDIT).append(id).toString(),
				((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
		User u = getUser(id);
		String loginName = u.getLoginName();
		if ("admin".equals(loginName)) {
			logger.info("Forbid to reset the supervisor password");
		} else {
			User user = new User();
			user.setId(id);
			user.setPassword(md5Pwd);
			try {
				result = userMapper.updateByPrimaryKeySelective(user);
			} catch (Exception e) {
				TMException.writeFail(logger, e);
			}
		}
		return result;
	}

	@Transactional(value = "transactionManager", rollbackFor = Exception.class)
	public int deleteUser(Long id, HttpServletRequest request) throws Exception {
		return batDeleteUser(id.toString());
	}

	@Transactional(value = "transactionManager", rollbackFor = Exception.class)
	public int batchDeleteUser(String ids, HttpServletRequest request) throws Exception {
		return batDeleteUser(ids);
	}

	@Transactional(value = "transactionManager", rollbackFor = Exception.class)
	public int batDeleteUser(String ids) throws Exception {
		int result = 0;
		StringBuffer sb = new StringBuffer();
		sb.append(BusinessConstants.LOG_OPERATION_TYPE_DELETE);
		List<User> list = getUserListByIds(ids);
		for (User user : list) {
			if (demonstrateOpen && user.getLoginName().equals(TEST_USER)) {
				logger.error("Exception code [{}], exception prompt [{}], parameter, ids: [{}]",
						ExceptionConstants.USER_LIMIT_DELETE_CODE, ExceptionConstants.USER_LIMIT_DELETE_MSG, ids);
				throw new BusinessRunTimeException(ExceptionConstants.USER_LIMIT_DELETE_CODE,
						ExceptionConstants.USER_LIMIT_DELETE_MSG);
			}
			if (user.getId().equals(user.getTenantId())) {
				logger.error("Exception code [{}], exception prompt [{}], parameter, ids: [{}]",
						ExceptionConstants.USER_LIMIT_TENANT_DELETE_CODE,
						ExceptionConstants.USER_LIMIT_TENANT_DELETE_MSG, ids);
				throw new BusinessRunTimeException(ExceptionConstants.USER_LIMIT_TENANT_DELETE_CODE,
						ExceptionConstants.USER_LIMIT_TENANT_DELETE_MSG);
			}
			sb.append("[").append(user.getLoginName()).append("]");
		}
		logService.insertLog("User", sb.toString(),
				((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
		String idsArray[] = ids.split(",");
		try {
			result = userMapperEx.batDeleteOrUpdateUser(idsArray, BusinessConstants.USER_STATUS_DELETE);
		} catch (Exception e) {
			TMException.writeFail(logger, e);
		}
		if (result < 1) {
			logger.error("Exception code [{}], exception prompt [{}], parameter, ids: [{}]",
					ExceptionConstants.USER_DELETE_FAILED_CODE, ExceptionConstants.USER_DELETE_FAILED_MSG, ids);
			throw new BusinessRunTimeException(ExceptionConstants.USER_DELETE_FAILED_CODE,
					ExceptionConstants.USER_DELETE_FAILED_MSG);
		}
		return result;
	}

	public int validateUser(String loginName, String password) throws Exception {
		/** The default is to log in */
		List<User> list = null;
		try {
			UserExample example = new UserExample();
			example.createCriteria().andLoginNameEqualTo(loginName);
			list = userMapper.selectByExample(example);
			if (null != list && list.size() == 0) {
				return ExceptionCodeConstants.UserExceptionCode.USER_NOT_EXIST;
			} else if (list.size() == 1) {
				if (list.get(0).getStatus() != 0) {
					return ExceptionCodeConstants.UserExceptionCode.BLACK_USER;
				}

			}
		} catch (Exception e) {
			logger.error(
					">>>>>>>>Access to verify whether there is background information abnormality in the user name", e);
			return ExceptionCodeConstants.UserExceptionCode.USER_ACCESS_EXCEPTION;
		}
		try {
			UserExample example = new UserExample();
			example.createCriteria().andLoginNameEqualTo(loginName).andPasswordEqualTo(password)
					.andStatusEqualTo(BusinessConstants.USER_STATUS_NORMAL);
			list = userMapper.selectByExample(example);
			if (null != list && list.size() == 0) {
				return ExceptionCodeConstants.UserExceptionCode.USER_PASSWORD_ERROR;
			}
		} catch (Exception e) {
			logger.error(">>>>>>>>>>Access to verify user password background information is abnormal", e);
			return ExceptionCodeConstants.UserExceptionCode.USER_ACCESS_EXCEPTION;
		}
		return ExceptionCodeConstants.UserExceptionCode.USER_CONDITION_FIT;
	}

	public User getUserByLoginName(String loginName) throws Exception {
		UserExample example = new UserExample();
		example.createCriteria().andLoginNameEqualTo(loginName).andStatusEqualTo(BusinessConstants.USER_STATUS_NORMAL);
		List<User> list = null;
		try {
			list = userMapper.selectByExample(example);
		} catch (Exception e) {
			TMException.readFail(logger, e);
		}
		User user = null;
		if (list != null && list.size() > 0) {
			user = list.get(0);
		}
		return user;
	}

	public int checkIsNameExist(Long id, String name) throws Exception {
		UserExample example = new UserExample();
		List<Byte> userStatus = new ArrayList<Byte>();
		userStatus.add(BusinessConstants.USER_STATUS_DELETE);
		userStatus.add(BusinessConstants.USER_STATUS_BANNED);
		example.createCriteria().andIdNotEqualTo(id).andLoginNameEqualTo(name).andStatusNotIn(userStatus);
		List<User> list = null;
		try {
			list = userMapper.selectByExample(example);
		} catch (Exception e) {
			TMException.readFail(logger, e);
		}
		return list == null ? 0 : list.size();
	}

	
	public User getCurrentUser() throws Exception {
		HttpServletRequest request = ((ServletRequestAttributes) Objects
				.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
		Long userId = Long.parseLong(redisService.getObjectFromSessionByKey(request, "userId").toString());
		return getUser(userId);
	}

	
	public Boolean checkIsTestUser() throws Exception {
		Boolean result = false;
		try {
			if (demonstrateOpen) {
				User user = getCurrentUser();
				if (TEST_USER.equals(user.getLoginName())) {
					result = true;
				}
			}
		} catch (Exception e) {
			TMException.readFail(logger, e);
		}
		return result;
	}

	
	public Long getIdByLoginName(String loginName) {
		Long userId = 0L;
		UserExample example = new UserExample();
		example.createCriteria().andLoginNameEqualTo(loginName).andStatusEqualTo(BusinessConstants.USER_STATUS_NORMAL);
		List<User> list = userMapper.selectByExample(example);
		if (list != null) {
			userId = list.get(0).getId();
		}
		return userId;
	}

	@Transactional(value = "transactionManager", rollbackFor = Exception.class)
	public void addUserAndOrgUserRel(UserEx ue, HttpServletRequest request) throws Exception {
		if (BusinessConstants.DEFAULT_MANAGER.equals(ue.getLoginName())) {
			throw new BusinessRunTimeException(ExceptionConstants.USER_NAME_LIMIT_USE_CODE,
					ExceptionConstants.USER_NAME_LIMIT_USE_MSG);
		} else {
			logService.insertLog("User", BusinessConstants.LOG_OPERATION_TYPE_ADD,
					((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
			// check username and login
			checkUserNameAndLoginName(ue);
			// Add user information
			ue = this.addUser(ue);
			if (ue == null) {
				logger.error("Exception code [{}], exception prompt [{}], parameter, [{}]",
						ExceptionConstants.USER_ADD_FAILED_CODE, ExceptionConstants.USER_ADD_FAILED_MSG);
				throw new BusinessRunTimeException(ExceptionConstants.USER_ADD_FAILED_CODE,
						ExceptionConstants.USER_ADD_FAILED_MSG);
			}
			// User id, query id according to user name
			Long userId = getIdByLoginName(ue.getLoginName());
			if (ue.getRoleId() != null) {
				JSONObject ubObj = new JSONObject();
				ubObj.put("type", "UserRole");
				ubObj.put("keyid", userId);
				ubObj.put("value", "[" + ue.getRoleId() + "]");
				userBusinessService.insertUserBusiness(ubObj, request);
			}
			if (ue.getOrgaId() == null) {
				// If no institution is selected, the association between the institution and
				// the user will not be established
				return;
			}

		}
	}

	@Transactional(value = "transactionManager", rollbackFor = Exception.class)
	public UserEx addUser(UserEx ue) throws Exception {
		
		ue.setPassword(Tools.md5Encryp(BusinessConstants.USER_DEFAULT_PASSWORD));
		ue.setIsystem(BusinessConstants.USER_NOT_SYSTEM);
		if (ue.getIsmanager() == null) {
			ue.setIsmanager(BusinessConstants.USER_NOT_MANAGER);
		}
		ue.setStatus(BusinessConstants.USER_STATUS_NORMAL);
		int result = 0;
		try {
			result = userMapper.insertSelective(ue);
		} catch (Exception e) {
			TMException.writeFail(logger, e);
		}
		if (result > 0) {
			return ue;
		}
		return null;
	}

	@Transactional(value = "transactionManager", rollbackFor = Exception.class)
	public UserEx registerUser(UserEx ue, Integer manageRoleId, HttpServletRequest request) throws Exception {

		if (BusinessConstants.DEFAULT_MANAGER.equals(ue.getLoginName())) {
			throw new BusinessRunTimeException(ExceptionConstants.USER_NAME_LIMIT_USE_CODE,
					ExceptionConstants.USER_NAME_LIMIT_USE_MSG);
		} else {
			ue.setPassword(ue.getPassword());
			ue.setIsystem(BusinessConstants.USER_NOT_SYSTEM);
			if (ue.getIsmanager() == null) {
				ue.setIsmanager(BusinessConstants.USER_NOT_MANAGER);
			}
			ue.setStatus(BusinessConstants.USER_STATUS_NORMAL);
			int result = 0;
			try {
				result = userMapper.insertSelective(ue);
				Long userId = getIdByLoginName(ue.getLoginName());
				ue.setId(userId);
			} catch (Exception e) {
				TMException.writeFail(logger, e);
			}
			// update tenant id
			User user = new User();
			user.setId(ue.getId());
			user.setTenantId(ue.getId());
			userService.updateUserTenant(user);
			// Add the relationship between user and role
			JSONObject ubObj = new JSONObject();
			ubObj.put("type", "UserRole");
			ubObj.put("keyid", ue.getId());
			JSONArray ubArr = new JSONArray();
			ubArr.add(manageRoleId);
			ubObj.put("value", ubArr.toString());
			ubObj.put("tenantId", ue.getId());
			userBusinessService.insertUserBusiness(ubObj, null);
			// Create tenant information
			JSONObject tenantObj = new JSONObject();
			tenantObj.put("tenantId", ue.getId());
			tenantObj.put("loginName", ue.getLoginName());
			tenantObj.put("userNumLimit", ue.getUserNumLimit());
			tenantObj.put("expireTime", ue.getExpireTime());
			tenantObj.put("remark", ue.getRemark());
			logger.info("==============Create tenant information complete=================");
			if (result > 0) {
				return ue;
			}
			return null;
		}
	}

	@Transactional(value = "transactionManager", rollbackFor = Exception.class)
	public void updateUserTenant(User user) throws Exception {
		UserExample example = new UserExample();
		example.createCriteria().andIdEqualTo(user.getId());
		try {
			userMapper.updateByPrimaryKeySelective(user);
		} catch (Exception e) {
			TMException.writeFail(logger, e);
		}
	}

	@Transactional(value = "transactionManager", rollbackFor = Exception.class)
	public void updateUserAndOrgUserRel(UserEx ue, HttpServletRequest request) throws Exception {
		if (BusinessConstants.DEFAULT_MANAGER.equals(ue.getLoginName())) {
			throw new BusinessRunTimeException(ExceptionConstants.USER_NAME_LIMIT_USE_CODE,
					ExceptionConstants.USER_NAME_LIMIT_USE_MSG);
		} else {
			if (demonstrateOpen && ue.getLoginName().equals(TEST_USER)) {
				logger.error("Exception code [{}], exception prompt [{}], parameter, obj:[{}]",
						ExceptionConstants.USER_LIMIT_UPDATE_CODE, ExceptionConstants.USER_LIMIT_UPDATE_MSG, TEST_USER);
				throw new BusinessRunTimeException(ExceptionConstants.USER_LIMIT_UPDATE_CODE,
						ExceptionConstants.USER_LIMIT_UPDATE_MSG);
			}
			logService.insertLog("User",
					new StringBuffer(BusinessConstants.LOG_OPERATION_TYPE_EDIT).append(ue.getId()).toString(),
					((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
			// check username and login
			checkUserNameAndLoginName(ue);
			// update user information
			ue = this.updateUser(ue);
			if (ue == null) {
				logger.error("Exception code [{}], exception prompt [{}], parameter, [{}]",
						ExceptionConstants.USER_EDIT_FAILED_CODE, ExceptionConstants.USER_EDIT_FAILED_MSG);
				throw new BusinessRunTimeException(ExceptionConstants.USER_EDIT_FAILED_CODE,
						ExceptionConstants.USER_EDIT_FAILED_MSG);
			}
			if (ue.getRoleId() != null) {
				JSONObject ubObj = new JSONObject();
				ubObj.put("type", "UserRole");
				ubObj.put("keyid", ue.getId());
				ubObj.put("value", "[" + ue.getRoleId() + "]");
				Long ubId = userBusinessService.checkIsValueExist("UserRole", ue.getId().toString());
				if (ubId != null) {
					ubObj.put("id", ubId);
					userBusinessService.updateUserBusiness(ubObj, request);
				} else {
					userBusinessService.insertUserBusiness(ubObj, request);
				}
			}

		}
	}

	@Transactional(value = "transactionManager", rollbackFor = Exception.class)
	public UserEx updateUser(UserEx ue) throws Exception {
		int result = 0;
		try {
			result = userMapper.updateByPrimaryKeySelective(ue);
		} catch (Exception e) {
			TMException.writeFail(logger, e);
		}
		if (result > 0) {
			return ue;
		}
		return null;
	}

	public void checkUserNameAndLoginName(UserEx userEx) throws Exception {
		List<User> list = null;
		if (userEx == null) {
			return;
		}
		Long userId = userEx.getId();
		// check login name
		if (!StringUtils.isEmpty(userEx.getLoginName())) {
			String loginName = userEx.getLoginName();
			list = this.getUserListByloginName(loginName);
			if (list != null && list.size() > 0) {
				if (list.size() > 1) {
					// More than one piece of data exists, the login name already exists
					logger.error("Exception code [{}], exception prompt [{}], parameter, loginName:[{}]",
							ExceptionConstants.USER_LOGIN_NAME_ALREADY_EXISTS_CODE,
							ExceptionConstants.USER_LOGIN_NAME_ALREADY_EXISTS_MSG, loginName);
					throw new BusinessRunTimeException(ExceptionConstants.USER_LOGIN_NAME_ALREADY_EXISTS_CODE,
							ExceptionConstants.USER_LOGIN_NAME_ALREADY_EXISTS_MSG);
				}
				// A piece of data, an exception is thrown when it is added, and an exception is
				// thrown when it is modified and the current id is different
				if (list.size() == 1) {
					if (userId == null || (userId != null && !userId.equals(list.get(0).getId()))) {
						logger.error("Exception code [{}], exception prompt [{}], parameter, loginName:[{}]",
								ExceptionConstants.USER_LOGIN_NAME_ALREADY_EXISTS_CODE,
								ExceptionConstants.USER_LOGIN_NAME_ALREADY_EXISTS_MSG, loginName);
						throw new BusinessRunTimeException(ExceptionConstants.USER_LOGIN_NAME_ALREADY_EXISTS_CODE,
								ExceptionConstants.USER_LOGIN_NAME_ALREADY_EXISTS_MSG);
					}
				}

			}
		}
		// check username
		if (!StringUtils.isEmpty(userEx.getUsername())) {
			String userName = userEx.getUsername();
			list = this.getUserListByUserName(userName);
			if (list != null && list.size() > 0) {
				if (list.size() > 1) {
					// More than one piece of data exists, the user name already exists
					logger.error("Exception code [{}], exception prompt [{}], parameter, userName:[{}]",
							ExceptionConstants.USER_USER_NAME_ALREADY_EXISTS_CODE,
							ExceptionConstants.USER_USER_NAME_ALREADY_EXISTS_MSG, userName);
					throw new BusinessRunTimeException(ExceptionConstants.USER_USER_NAME_ALREADY_EXISTS_CODE,
							ExceptionConstants.USER_USER_NAME_ALREADY_EXISTS_MSG);
				}
				// A piece of data, an exception is thrown when it is added, and an exception is
				// thrown when it is modified and the current id is different
				if (list.size() == 1) {
					if (userId == null || (userId != null && !userId.equals(list.get(0).getId()))) {
						logger.error("Exception code [{}], exception prompt [{}], parameter, userName:[{}]",
								ExceptionConstants.USER_USER_NAME_ALREADY_EXISTS_CODE,
								ExceptionConstants.USER_USER_NAME_ALREADY_EXISTS_MSG, userName);
						throw new BusinessRunTimeException(ExceptionConstants.USER_USER_NAME_ALREADY_EXISTS_CODE,
								ExceptionConstants.USER_USER_NAME_ALREADY_EXISTS_MSG);
					}
				}

			}
		}

	}

	/**
	 * Get user list by username
	 */
	public List<User> getUserListByUserName(String userName) throws Exception {
		List<User> list = null;
		try {
			list = userMapperEx.getUserListByUserNameOrLoginName(userName, null);
		} catch (Exception e) {
			TMException.readFail(logger, e);
		}
		return list;
	}

	/**
	 * Get user list by login name
	 */
	public List<User> getUserListByloginName(String loginName) {
		List<User> list = null;
		try {
			list = userMapperEx.getUserListByUserNameOrLoginName(null, loginName);
		} catch (Exception e) {
			TMException.readFail(logger, e);
		}
		return list;
	}

	/**
	 * Get user id
	 * 
	 * @param request
	 * @return
	 */
	public Long getUserId(HttpServletRequest request) throws Exception {
		Object userIdObj = redisService.getObjectFromSessionByKey(request, "userId");
		Long userId = null;
		if (userIdObj != null) {
			userId = Long.parseLong(userIdObj.toString());
		}
		return userId;
	}

	/**
	 * User's button permissions
	 * 
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public JSONArray getBtnStrArrById(Long userId) throws Exception {
		JSONArray btnStrArr = new JSONArray();
		List<UserBusiness> userRoleList = userBusinessService.getBasicData(userId.toString(), "UserRole");
		if (userRoleList != null && userRoleList.size() > 0) {
			String roleValue = userRoleList.get(0).getValue();
			if (StringUtil.isNotEmpty(roleValue) && roleValue.indexOf("[") > -1 && roleValue.indexOf("]") > -1) {
				roleValue = roleValue.replace("[", "").replace("]", ""); // role id-single
				List<UserBusiness> roleFunctionsList = userBusinessService.getBasicData(roleValue, "RoleFunctions");
				if (roleFunctionsList != null && roleFunctionsList.size() > 0) {
					String btnStr = roleFunctionsList.get(0).getBtnStr();
					if (StringUtil.isNotEmpty(btnStr)) {
						btnStrArr = JSONArray.parseArray(btnStr);
					}
				}
			}
		}
		return btnStrArr;
	}

	@Transactional(value = "transactionManager", rollbackFor = Exception.class)
	public int batchSetStatus(Byte status, String ids) throws Exception {
		int result = 0;
		List<User> list = getUserListByIds(ids);
		for (User user : list) {
			if (demonstrateOpen && user.getLoginName().equals(TEST_USER)) {
				logger.error("Exception code [{}], exception prompt [{}], parameter, obj:[{}]",
						ExceptionConstants.USER_LIMIT_UPDATE_CODE, ExceptionConstants.USER_LIMIT_UPDATE_MSG, TEST_USER);
				throw new BusinessRunTimeException(ExceptionConstants.USER_LIMIT_UPDATE_CODE,
						ExceptionConstants.USER_LIMIT_UPDATE_MSG);
			}
		}
		String statusStr = "";
		if (status == 0) {
			statusStr = "Batch Enable";
		} else if (status == 2) {
			statusStr = "Batch Disable";
		}
		logService.insertLog("User",
				new StringBuffer(BusinessConstants.LOG_OPERATION_TYPE_EDIT).append(ids).append("-").append(statusStr)
						.toString(),
				((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
		List<Long> idList = StringUtil.strToLongList(ids);
		User user = new User();
		user.setStatus(status);
		UserExample example = new UserExample();
		example.createCriteria().andIdIn(idList);
		result = userMapper.updateByExampleSelective(user, example);
		return result;
	}
}
