package com.tmlab.erp.exception;

import org.slf4j.Logger;

import com.tmlab.erp.constants.ExceptionConstants;

/**
  * Encapsulate log printing, collect logs
  * author: SD
  */
public class TMException {

     public static void readFail(Logger logger, Exception e) {
         logger.error("Exception code [{}], exception prompt [{}], exception [{}]",
                 ExceptionConstants.DATA_READ_FAIL_CODE, ExceptionConstants.DATA_READ_FAIL_MSG,e);
         throw new BusinessRunTimeException(ExceptionConstants.DATA_READ_FAIL_CODE,
                 ExceptionConstants.DATA_READ_FAIL_MSG);
     }

     public static void writeFail(Logger logger, Exception e) {
         logger.error("Exception code [{}], exception prompt [{}], exception [{}]",
                 ExceptionConstants.DATA_WRITE_FAIL_CODE,ExceptionConstants.DATA_WRITE_FAIL_MSG,e);
         throw new BusinessRunTimeException(ExceptionConstants.DATA_WRITE_FAIL_CODE,
                 ExceptionConstants.DATA_WRITE_FAIL_MSG);
     }


}