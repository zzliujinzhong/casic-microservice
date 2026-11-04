package com.casic.microservice.common.security.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * JWT 配置属性
 */
@Data
@ConfigurationProperties(prefix = "casic.security.jwt")
public class JwtProperties {

    /** JWT 密钥 */
    private String secret = "CASIC-MICROSERVICE-JWT-SECRET-KEY-2024-REPLACE-IN-PRODUCTION";

    /** Token 过期时间（秒） */
    private long expireSeconds = 7200L;

    /** 是否启用 JWT 认证 */
    private boolean enabled = true;

    /** 白名单路径 */
    private String[] whitelist = new String[]{
            "/auth/**",
            "/actuator/**",
            "/error",
            "/doc.html",
            "/webjars/**",
            "/swagger-resources/**",
            "/v2/api-docs"
    };
}
