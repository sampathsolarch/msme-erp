package com.tmlab.erp.datasource.mappers;

import org.apache.ibatis.annotations.Param;

public interface LogMapperEx {

	Long countsByLog(@Param("operation") String operation, @Param("userInfo") String userInfo,
			@Param("clientIp") String clientIp, @Param("status") Integer status, @Param("beginTime") String beginTime,
			@Param("endTime") String endTime, @Param("content") String content);

	Long getCountByIpAndDate(@Param("moduleName") String moduleName, @Param("clientIp") String clientIp,
			@Param("createTime") String createTime);
}