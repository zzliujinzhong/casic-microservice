package com.casic.microservice.auth.controller;

import com.casic.microservice.common.core.result.R;
import com.casic.microservice.common.security.utils.JwtUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 认证控制器
 */
@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Value("${casic.security.jwt.secret}")
    private String jwtSecret;

    /**
     * 登录接口（示例）
     */
    @PostMapping("/login")
    public R<Map<String, Object>> login(@RequestBody LoginDTO loginDTO) {
        log.info("用户登录: {}", loginDTO.getUsername());

        // TODO: 实际场景中查询数据库校验用户名密码
        Long userId = 1L;
        String userName = loginDTO.getUsername();

        // 生成 JWT Token
        String token = JwtUtils.generateToken(userId, userName, jwtSecret, JwtUtils.DEFAULT_EXPIRE_SECONDS);

        Map<String, Object> result = new HashMap<>();
        result.put("token", token);
        result.put("tokenType", "Bearer");
        result.put("expiresIn", JwtUtils.DEFAULT_EXPIRE_SECONDS);
        result.put("userId", userId);
        result.put("userName", userName);

        return R.ok("登录成功", result);
    }

    /**
     * 刷新 Token
     */
    @PostMapping("/refresh")
    public R<Map<String, Object>> refresh(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        Long userId = JwtUtils.getUserId(token);
        String userName = JwtUtils.getUserName(token);

        String newToken = JwtUtils.generateToken(userId, userName, jwtSecret, JwtUtils.DEFAULT_EXPIRE_SECONDS);

        Map<String, Object> result = new HashMap<>();
        result.put("token", newToken);
        result.put("expiresIn", JwtUtils.DEFAULT_EXPIRE_SECONDS);
        return R.ok(result);
    }

    /**
     * 注册接口（示例）
     */
    @PostMapping("/register")
    public R<Boolean> register(@RequestBody LoginDTO registerDTO) {
        log.info("用户注册: {}", registerDTO.getUsername());
        // TODO: 实际注册逻辑
        return R.ok(true);
    }

    /**
     * 登出
     */
    @PostMapping("/logout")
    public R<Boolean> logout() {
        // TODO: 将 Token 加入黑名单或从 Redis 删除
        return R.ok(true);
    }

    @Data
    public static class LoginDTO {
        private String username;
        private String password;
        private String captcha;
    }
}
