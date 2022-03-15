package com.tmlab.erp.service.redis;

import com.tmlab.erp.constants.BusinessConstants;
import com.tmlab.erp.utils.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Description
 *
 * @author SD
 * 
 */
@Component
public class RedisService {

	@Resource
	public RedisTemplate redisTemplate;

	public static final String ACCESS_TOKEN = "X-Access-Token";

	@Autowired(required = false)
	public void setRedisTemplate(RedisTemplate redisTemplate) {
		RedisSerializer stringSerializer = new StringRedisSerializer();
		redisTemplate.setKeySerializer(stringSerializer);
		redisTemplate.setValueSerializer(stringSerializer);
		redisTemplate.setHashKeySerializer(stringSerializer);
		redisTemplate.setHashValueSerializer(stringSerializer);
		this.redisTemplate = redisTemplate;
	}

	/**
	 * @author SD description: Session information
	 * @Param: request
	 * @Param: key
	 * @return Object
	 */
	public Object getObjectFromSessionByKey(HttpServletRequest request, String key) {
		Object obj = null;
		if (request == null) {
			return null;
		}
		String token = request.getHeader(ACCESS_TOKEN);
		if (token != null) {
			// Open redis, put user data in redis, get it from redis
			if (redisTemplate.opsForHash().hasKey(token, key)) {
				// Exist in redis, take it out and use it
				obj = redisTemplate.opsForHash().get(token, key);
				redisTemplate.expire(token, BusinessConstants.MAX_SESSION_IN_SECONDS, TimeUnit.SECONDS);
			}
		}
		return obj;
	}

	public void storageObjectBySession(String token, String key, Object obj) {
		// Open redis, put user data into redis
		redisTemplate.opsForHash().put(token, key, obj.toString());
		redisTemplate.expire(token, BusinessConstants.MAX_SESSION_IN_SECONDS, TimeUnit.SECONDS);
	}

	public void deleteObjectBySession(HttpServletRequest request, String key) {
		if (request != null) {
			String token = request.getHeader(ACCESS_TOKEN);
			if (StringUtil.isNotEmpty(token)) {
				// Open redis, put user data in redis, delete from redis
				redisTemplate.opsForHash().delete(token, key);
			}
		}
	}

	public void deleteObjectByKeyAndIp(String key, String ip, String deleteKey) {
		Set<String> tokens = redisTemplate.keys("*");
		for (String token : tokens) {
			Object value = redisTemplate.opsForHash().get(token, key);
			if (value != null && value.equals(ip)) {
				redisTemplate.opsForHash().delete(token, deleteKey);
			}
		}
	}
}