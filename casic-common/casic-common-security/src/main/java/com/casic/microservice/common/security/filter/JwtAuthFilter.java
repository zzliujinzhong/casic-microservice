package com.casic.microservice.common.security.filter;

import com.casic.microservice.common.core.constant.CommonConstants;
import com.casic.microservice.common.core.context.UserContext;
import com.casic.microservice.common.security.config.JwtProperties;
import com.casic.microservice.common.security.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * JWT 认证过滤器
 */
@Slf4j
public class JwtAuthFilter extends OncePerRequestFilter {

    @Resource
    private JwtProperties jwtProperties;

    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        if (!jwtProperties.isEnabled()) {
            return true;
        }
        String requestPath = request.getRequestURI();
        for (String pattern : jwtProperties.getWhitelist()) {
            if (pathMatcher.match(pattern, requestPath)) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader = request.getHeader(CommonConstants.TOKEN_HEADER);

        if (StringUtils.hasText(authHeader) && authHeader.startsWith(CommonConstants.TOKEN_PREFIX)) {
            String token = authHeader.substring(CommonConstants.TOKEN_PREFIX.length());
            try {
                if (JwtUtils.validateToken(token, jwtProperties.getSecret())) {
                    Claims claims = JwtUtils.parseToken(token, jwtProperties.getSecret());
                    if (claims != null) {
                        UserContext.UserInfo userInfo = new UserContext.UserInfo();
                        Object userId = claims.get("userId");
                        if (userId instanceof Number) {
                            userInfo.setUserId(((Number) userId).longValue());
                        }
                        userInfo.setUserName(claims.get("userName", String.class));
                        UserContext.set(userInfo);
                    }
                }
            } catch (Exception e) {
                log.warn("JWT Token 处理失败: {}", e.getMessage());
            }
        }

        try {
            filterChain.doFilter(request, response);
        } finally {
            UserContext.clear();
        }
    }
}
