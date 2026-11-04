package com.casic.microservice.contract.feign;

import com.casic.microservice.common.core.result.R;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Feign 降级处理
 */
@Slf4j
@Component
public class AuthFeignClientFallback implements FallbackFactory<AuthFeignClient> {

    @Override
    public AuthFeignClient create(Throwable cause) {
        log.warn("Auth 服务调用降级: {}", cause.getMessage());
        return token -> R.fail("Auth 服务暂不可用，请稍后重试");
    }
}
