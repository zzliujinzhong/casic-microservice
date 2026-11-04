package com.casic.microservice.contract.feign;

import com.casic.microservice.common.core.result.R;
import lombok.Data;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 远程调用 Auth 服务示例
 */
@FeignClient(name = "casic-auth", fallbackFactory = AuthFeignClientFallback.class)
public interface AuthFeignClient {

    /**
     * 验证 Token
     */
    @GetMapping("/auth/verify")
    R<VerifyResult> verifyToken(@RequestParam("token") String token);

    @Data
    class VerifyResult {
        private Long userId;
        private String userName;
        private boolean valid;
    }
}
