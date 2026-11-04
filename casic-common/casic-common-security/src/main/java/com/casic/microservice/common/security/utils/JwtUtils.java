package com.casic.microservice.common.security.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT 工具类 - 基于 JJWT 0.11.x
 */
@Slf4j
public class JwtUtils {

    private JwtUtils() {}

    /** 默认密钥（生产环境必须替换） */
    private static final String DEFAULT_SECRET = "CASIC-MICROSERVICE-JWT-SECRET-KEY-2024-REPLACE-IN-PRODUCTION";

    /** 默认过期时间（秒）- 2小时 */
    public static final long DEFAULT_EXPIRE_SECONDS = 7200L;

    /** 默认刷新 Token 过期时间（秒）- 7天 */
    public static final long REFRESH_EXPIRE_SECONDS = 604800L;

    /**
     * 生成 JWT Token
     */
    public static String generateToken(Long userId, String userName, String secret, long expireSeconds) {
        long now = System.currentTimeMillis();
        Date expiration = new Date(now + expireSeconds * 1000);

        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        claims.put("userName", userName);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(now))
                .setExpiration(expiration)
                .signWith(getSignKey(secret), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * 生成 JWT Token（使用默认密钥和过期时间）
     */
    public static String generateToken(Long userId, String userName) {
        return generateToken(userId, userName, DEFAULT_SECRET, DEFAULT_EXPIRE_SECONDS);
    }

    /**
     * 解析 JWT Token
     */
    public static Claims parseToken(String token, String secret) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSignKey(secret))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            log.warn("JWT Token 已过期: {}", e.getMessage());
            return e.getClaims();
        } catch (Exception e) {
            log.warn("JWT Token 解析失败: {}", e.getMessage());
            return null;
        }
    }

    /**
     * 解析 JWT Token（使用默认密钥）
     */
    public static Claims parseToken(String token) {
        return parseToken(token, DEFAULT_SECRET);
    }

    /**
     * 验证 Token 是否有效（未过期且签名正确）
     */
    public static boolean validateToken(String token, String secret) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSignKey(secret))
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            log.warn("JWT Token 已过期");
            return false;
        } catch (Exception e) {
            log.warn("JWT Token 无效: {}", e.getMessage());
            return false;
        }
    }

    public static boolean validateToken(String token) {
        return validateToken(token, DEFAULT_SECRET);
    }

    /**
     * 判断 Token 是否过期
     */
    public static boolean isTokenExpired(String token, String secret) {
        Claims claims = parseToken(token, secret);
        return claims == null || claims.getExpiration().before(new Date());
    }

    /**
     * 从 Token 获取用户 ID
     */
    public static Long getUserId(String token) {
        Claims claims = parseToken(token);
        if (claims == null) return null;
        Object userId = claims.get("userId");
        if (userId instanceof Number) {
            return ((Number) userId).longValue();
        }
        return Long.parseLong(userId.toString());
    }

    /**
     * 从 Token 获取用户名
     */
    public static String getUserName(String token) {
        Claims claims = parseToken(token);
        return claims != null ? claims.get("userName", String.class) : null;
    }

    /**
     * 获取签名密钥
     */
    private static SecretKey getSignKey(String secret) {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }
}
