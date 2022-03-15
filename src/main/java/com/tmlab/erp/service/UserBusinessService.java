package com.tmlab.erp.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSONObject;
import com.tmlab.erp.constants.BusinessConstants;
import com.tmlab.erp.datasource.entities.User;
import com.tmlab.erp.datasource.entities.UserBusiness;
import com.tmlab.erp.datasource.entities.UserBusinessExample;
import com.tmlab.erp.datasource.mappers.UserBusinessMapper;
import com.tmlab.erp.datasource.mappers.UserBusinessMapperEx;
import com.tmlab.erp.exception.TMException;
import com.tmlab.erp.utils.Tools;

@Service
public class UserBusinessService {
	private Logger logger = LoggerFactory.getLogger(UserBusinessService.class);

	@Resource
	private UserBusinessMapper userBusinessMapper;
	@Resource
	private UserBusinessMapperEx userBusinessMapperEx;
	@Resource
	private LogService logService;
	@Resource
	private UserService userService;

	public UserBusiness getUserBusiness(long id) throws Exception {
		UserBusiness result = null;
		try {
			result = userBusinessMapper.selectByPrimaryKey(id);
		} catch (Exception e) {
			TMException.readFail(logger, e);
		}
		return result;
	}

	public List<UserBusiness> getUserBusiness() throws Exception {
		UserBusinessExample example = new UserBusinessExample();
		example.createCriteria().andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
		List<UserBusiness> list = null;
		try {
			list = userBusinessMapper.selectByExample(example);
		} catch (Exception e) {
			TMException.readFail(logger, e);
		}
		return list;
	}

	@Transactional(value = "transactionManager", rollbackFor = Exception.class)
	public int insertUserBusiness(JSONObject obj, HttpServletRequest request) throws Exception {
		UserBusiness userBusiness = JSONObject.parseObject(obj.toJSONString(), UserBusiness.class);
		int result = 0;
		try {
			String token = "";
			if (request != null) {
				token = request.getHeader("X-Access-Token");
				Long tenantId = Tools.getTenantIdByToken(token);
				if (tenantId != 0L) {
					userBusiness.setTenantId(tenantId);
				}
			}
			String value = userBusiness.getValue();
			String newValue = value.replaceAll(",", "\\]\\[");
			newValue = newValue.replaceAll("\\[0\\]", "").replaceAll("\\[\\]", "");
			userBusiness.setValue(newValue);
			result = userBusinessMapper.insertSelective(userBusiness);
			logService.insertLog("Connection Relation", BusinessConstants.LOG_OPERATION_TYPE_ADD, request);
		} catch (Exception e) {
			TMException.writeFail(logger, e);
		}
		return result;
	}

	@Transactional(value = "transactionManager", rollbackFor = Exception.class)
	public int updateUserBusiness(JSONObject obj, HttpServletRequest request) throws Exception {
		UserBusiness userBusiness = JSONObject.parseObject(obj.toJSONString(), UserBusiness.class);
		int result = 0;
		try {
			String value = userBusiness.getValue();
			String newValue = value.replaceAll(",", "\\]\\[");
			newValue = newValue.replaceAll("\\[0\\]", "").replaceAll("\\[\\]", "");
			userBusiness.setValue(newValue);
			result = userBusinessMapper.updateByPrimaryKeySelective(userBusiness);
			logService.insertLog("Connection Relation", BusinessConstants.LOG_OPERATION_TYPE_EDIT, request);
		} catch (Exception e) {
			TMException.writeFail(logger, e);
		}
		return result;
	}

	@Transactional(value = "transactionManager", rollbackFor = Exception.class)
	public int deleteUserBusiness(Long id, HttpServletRequest request) throws Exception {
		return batchDeleteUserBusinessByIds(id.toString());
	}

	@Transactional(value = "transactionManager", rollbackFor = Exception.class)
	public int batchDeleteUserBusiness(String ids, HttpServletRequest request) throws Exception {
		return batchDeleteUserBusinessByIds(ids);
	}

	@Transactional(value = "transactionManager", rollbackFor = Exception.class)
	public int batchDeleteUserBusinessByIds(String ids) throws Exception {
		logService.insertLog("Connection Relation",
				new StringBuffer(BusinessConstants.LOG_OPERATION_TYPE_DELETE).append(ids).toString(),
				((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
		User userInfo = userService.getCurrentUser();
		String[] idArray = ids.split(",");
		int result = 0;
		try {
			result = userBusinessMapperEx.batchDeleteUserBusinessByIds(new Date(),
					userInfo == null ? null : userInfo.getId(), idArray);
		} catch (Exception e) {
			TMException.writeFail(logger, e);
		}
		return result;
	}

	public int checkIsNameExist(Long id, String name) throws Exception {
		return 1;
	}

	public List<UserBusiness> getBasicData(String keyId, String type) throws Exception {
		List<UserBusiness> list = null;
		try {
			UserBusinessExample example = new UserBusinessExample();
			example.createCriteria().andKeyIdEqualTo(keyId).andTypeEqualTo(type)
					.andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
			list = userBusinessMapper.selectByExample(example);
		} catch (Exception e) {
			TMException.readFail(logger, e);
		}
		return list;
	}

	public List<UserBusiness> getListBy(String keyId, String type) throws Exception {
		List<UserBusiness> list = null;
		try {
			UserBusinessExample example = new UserBusinessExample();
			example.createCriteria().andKeyIdEqualTo(keyId).andTypeEqualTo(type)
					.andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
			list = userBusinessMapper.selectByExample(example);
		} catch (Exception e) {
			TMException.readFail(logger, e);
		}
		return list;
	}

	public String getUBValueByTypeAndKeyId(String type, String keyId) throws Exception {
		String ubValue = "";
		List<UserBusiness> ubList = getBasicData(keyId, type);
		if (ubList != null && ubList.size() > 0) {
			ubValue = ubList.get(0).getValue();
		}
		return ubValue;
	}

	public Long checkIsValueExist(String type, String keyId) throws Exception {
		UserBusinessExample example = new UserBusinessExample();
		example.createCriteria().andTypeEqualTo(type).andKeyIdEqualTo(keyId)
				.andDeleteFlagNotEqualTo(BusinessConstants.DELETE_FLAG_DELETED);
		List<UserBusiness> list = null;
		try {
			list = userBusinessMapper.selectByExample(example);
		} catch (Exception e) {
			TMException.readFail(logger, e);
		}
		Long id = null;
		if (list != null && list.size() > 0) {
			id = list.get(0).getId();
		}
		return id;
	}

	@Transactional(value = "transactionManager", rollbackFor = Exception.class)
	public int updateBtnStr(String keyId, String type, String btnStr) throws Exception {
		logService.insertLog("Association",
				new StringBuffer(BusinessConstants.LOG_OPERATION_TYPE_EDIT).append("Role's button permissions").toString(),
				((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
		UserBusiness userBusiness = new UserBusiness();
		userBusiness.setBtnStr(btnStr);
		UserBusinessExample example = new UserBusinessExample();
		example.createCriteria().andKeyIdEqualTo(keyId).andTypeEqualTo(type);
		int result = 0;
		try {
			result = userBusinessMapper.updateByExampleSelective(userBusiness, example);
		} catch (Exception e) {
			TMException.writeFail(logger, e);
		}
		return result;
	}
}
