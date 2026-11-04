package com.casic.microservice.common.core.result;

import lombok.Getter;

/**
 * 全局响应状态码
 */
@Getter
public enum ResultCode {

    SUCCESS(200, "操作成功"),
    FAIL(500, "操作失败"),

    // 参数校验 400-499
    BAD_REQUEST(400, "请求参数错误"),
    UNAUTHORIZED(401, "未授权，请先登录"),
    FORBIDDEN(403, "禁止访问"),
    NOT_FOUND(404, "资源不存在"),
    METHOD_NOT_ALLOWED(405, "请求方法不允许"),
    PARAM_VALID_ERROR(422, "参数校验失败"),

    // 业务异常 1000-1999
    BUSINESS_ERROR(1000, "业务处理异常"),

    // 权限相关 2000-2999
    TOKEN_EXPIRED(2001, "Token已过期"),
    TOKEN_INVALID(2002, "Token无效"),
    TOKEN_MISSING(2003, "Token缺失"),
    PERMISSION_DENIED(2004, "权限不足"),

    // 数据库相关 3000-3999
    DATA_NOT_FOUND(3001, "数据不存在"),
    DATA_DUPLICATE(3002, "数据重复"),
    DATA_INTEGRITY_VIOLATION(3003, "数据完整性违规"),

    // 远程调用 4000-4999
    REMOTE_CALL_FAILED(4001, "远程服务调用失败"),
    REMOTE_CALL_TIMEOUT(4002, "远程服务调用超时"),

    // 限流熔断 5000-5999
    FLOW_LIMIT(5001, "请求过于频繁，请稍后再试"),
    DEGRADE(5002, "服务降级中"),
    CIRCUIT_BREAKER(5003, "服务熔断中"),

    // 系统异常 9000-9999
    SYSTEM_ERROR(9000, "系统内部错误"),
    CONFIG_ERROR(9001, "系统配置错误"),
    IO_ERROR(9002, "IO操作异常");

    private final int code;
    private final String msg;

    ResultCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
