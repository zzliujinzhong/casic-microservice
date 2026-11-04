package com.casic.microservice.common.core.exception;

import com.casic.microservice.common.core.result.ResultCode;

/**
 * 认证异常
 */
public class AuthException extends BusinessException {

    private static final long serialVersionUID = 1L;

    public AuthException(String msg) {
        super(ResultCode.UNAUTHORIZED, msg);
    }

    public AuthException(ResultCode resultCode, String msg) {
        super(resultCode, msg);
    }
}
