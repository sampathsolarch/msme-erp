package com.tmlab.erp.utils;

public interface ExceptionCodeConstants {
     /**
      * User error code definition
      */
     public class UserExceptionCode {
         /**
          * User does not exist
          */
         public static final int USER_NOT_EXIST = 1;

         /**
          * User password error
          */
         public static final int USER_PASSWORD_ERROR = 2;

         /**
          * User is blacklisted
          */
         public static final int BLACK_USER = 3;

         /**
          * can log in
          */
         public static final int USER_CONDITION_FIT = 4;

         /**
          * Access database exception
          */
         public static final int USER_ACCESS_EXCEPTION = 5;

         /**
          * Tenant is blacklisted
          */
         public static final int BLACK_TENANT = 6;

         /**
          * Tenant has expired
          */
         public static final int EXPIRE_TENANT = 7;
     }
}