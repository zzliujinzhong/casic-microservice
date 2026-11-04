package com.casic.microservice.common.core.context;

import com.alibaba.ttl.TransmittableThreadLocal;
import lombok.Data;

/**
 * 用户上下文 - 基于 ThreadLocal 存储当前登录用户信息
 * 支持跨线程传递（配合 TransmittableThreadLocal）
 */
public final class UserContext {

    private static final ThreadLocal<UserInfo> HOLDER = new TransmittableThreadLocal<>();

    private UserContext() {}

    public static void set(UserInfo userInfo) {
        HOLDER.set(userInfo);
    }

    public static UserInfo get() {
        return HOLDER.get();
    }

    public static void clear() {
        HOLDER.remove();
    }

    public static Long getUserId() {
        UserInfo info = HOLDER.get();
        return info != null ? info.getUserId() : null;
    }

    public static String getUserName() {
        UserInfo info = HOLDER.get();
        return info != null ? info.getUserName() : null;
    }

    public static Long getTenantId() {
        UserInfo info = HOLDER.get();
        return info != null ? info.getTenantId() : null;
    }

    @Data
    public static class UserInfo {
        private Long userId;
        private String userName;
        private String nickName;
        private Long tenantId;
        private String deviceType;
    }
}
