package com.tmlab.erp.exception;

import com.alibaba.fastjson.JSONObject;
import com.tmlab.erp.constants.ExceptionConstants;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(value = Exception.class)
	@ResponseBody
	public Object handleException(Exception e, HttpServletRequest request) {
		JSONObject status = new JSONObject();

		if (e instanceof BusinessParamCheckingException) {
			status.put(ExceptionConstants.GLOBAL_RETURNS_CODE, ((BusinessParamCheckingException) e).getCode());
			status.put(ExceptionConstants.GLOBAL_RETURNS_DATA, ((BusinessParamCheckingException) e).getData());
			return status;
		}

		if (e instanceof BusinessRunTimeException) {
			status.put(ExceptionConstants.GLOBAL_RETURNS_CODE, ((BusinessRunTimeException) e).getCode());
			status.put(ExceptionConstants.GLOBAL_RETURNS_DATA, ((BusinessRunTimeException) e).getData());
			return status;
		}

		status.put(ExceptionConstants.GLOBAL_RETURNS_CODE, ExceptionConstants.SERVICE_SYSTEM_ERROR_CODE);
		status.put(ExceptionConstants.GLOBAL_RETURNS_DATA, ExceptionConstants.SERVICE_SYSTEM_ERROR_MSG);
		// log.error("Global Exception Occured => url : {}, msg : {}",
		// request.getRequestURL(), e.getMessage());

		// log.error("Global Exception Occured => url : {}", request.getRequestURL(),
		// e);
		e.printStackTrace();
		return status;
	}
}