package com.tmlab.erp.utils;

/**
 *
 */
public enum ErpInfo {
    // pass parameters through construction
    OK(200, "Success"),
    BAD_REQUEST(400, "Request error or parameter error"),
    UNAUTHORIZED(401, "Unauthenticated user"),
    INVALID_VERIFY_CODE(461, "Incorrect verification code"),
    ERROR(500, "Service Internal Error"),
    WARING_MSG(201, "Warning message"),
    REDIRECT(301, "Session invalid, redirect"),
    FORWARD_REDIRECT(302, "Invalid forwarding request session"),
    FORWARD_FAILED(303, "Failed to forward request!"),
    TEST_USER(-1, "Demo user prohibited operation");

    public final int code;
    public final String name;

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    /**
     * Define the enumeration constructor
     */
    ErpInfo(int code, String name) {
        this.code = code;
        this.name = name;
    }
}
