package com.casic.microservice.common.core.exception;

import com.casic.microservice.common.core.result.ResultCode;

/**
 * 参数校验异常
 */
public class ParamValidException extends BusinessException {

    private static final long serialVersionUID = 1L;

    public ParamValidException(String msg) {
        super(ResultCode.PARAM_VALID_ERROR, msg);
    }

    public ParamValidException(ResultCode resultCode, String msg) {
        super(resultCode, msg);
    }
}
