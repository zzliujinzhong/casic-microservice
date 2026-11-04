package com.casic.microservice.contract.config;

import com.alibaba.csp.sentinel.annotation.aspectj.SentinelResourceAspect;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * Sentinel 配置（可通过 Nacos 动态配置规则）
 */
@Configuration
public class SentinelConfig {

    @Bean
    public SentinelResourceAspect sentinelResourceAspect() {
        return new SentinelResourceAspect();
    }

    /**
     * 初始化默认限流规则（实际项目推荐通过 Nacos 推送）
     */
    @PostConstruct
    public void initDefaultRules() {
        List<FlowRule> rules = new ArrayList<>();

        // 合同查询接口限流
        FlowRule queryRule = new FlowRule();
        queryRule.setResource("contract:query");
        queryRule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        queryRule.setCount(100);
        rules.add(queryRule);

        // 合同创建接口限流
        FlowRule createRule = new FlowRule();
        createRule.setResource("contract:create");
        createRule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        createRule.setCount(20);
        rules.add(createRule);

        FlowRuleManager.loadRules(rules);
    }
}
