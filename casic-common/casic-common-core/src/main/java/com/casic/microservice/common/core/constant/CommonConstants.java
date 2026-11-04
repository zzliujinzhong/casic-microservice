package com.casic.microservice.common.core.constant;

/**
 * 全局常量定义
 */
public final class CommonConstants {

    private CommonConstants() {}

    /** 成功标记 */
    public static final String SUCCESS = "0";

    /** 失败标记 */
    public static final String FAIL = "1";

    /** UTF-8 编码 */
    public static final String UTF_8 = "UTF-8";

    /** HTTP 相关 */
    public static final String HTTP = "http://";
    public static final String HTTPS = "https://";

    /** Header 常量 */
    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String TRACE_ID = "X-Trace-Id";
    public static final String USER_ID = "X-User-Id";
    public static final String USER_NAME = "X-User-Name";
    public static final String TENANT_ID = "X-Tenant-Id";
    public static final String DEVICE_TYPE = "X-Device-Type";

    /** 默认值 */
    public static final int DEFAULT_PAGE = 1;
    public static final int DEFAULT_SIZE = 10;
    public static final int MAX_SIZE = 100;
    public static final int YES = 1;
    public static final int NO = 0;

    /** 逻辑删除 */
    public static final int DELETED_YES = 1;
    public static final int DELETED_NO = 0;

    /** 缓存 Key 前缀 */
    public static final String CACHE_PREFIX = "casic:";
    public static final String CACHE_TOKEN = CACHE_PREFIX + "token:";
    public static final String CACHE_USER = CACHE_PREFIX + "user:";
    public static final String CACHE_DICT = CACHE_PREFIX + "dict:";
}
