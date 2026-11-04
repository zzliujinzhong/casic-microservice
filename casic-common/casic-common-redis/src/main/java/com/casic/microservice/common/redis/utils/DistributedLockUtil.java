package com.casic.microservice.common.redis.utils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * Redisson 分布式锁工具
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DistributedLockUtil {

    private final RedissonClient redissonClient;

    /**
     * 尝试获取锁
     */
    public boolean tryLock(String lockKey, long waitTime, long leaseTime, TimeUnit unit) {
        RLock lock = redissonClient.getLock(lockKey);
        try {
            return lock.tryLock(waitTime, leaseTime, unit);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.warn("获取分布式锁被中断: {}", lockKey);
            return false;
        }
    }

    /**
     * 释放锁
     */
    public void unlock(String lockKey) {
        RLock lock = redissonClient.getLock(lockKey);
        if (lock.isHeldByCurrentThread()) {
            lock.unlock();
        }
    }

    /**
     * 带锁执行（try-finally 自动释放）
     */
    public <T> T executeWithLock(String lockKey, long waitTime, long leaseTime,
                                  TimeUnit unit, Supplier<T> action) {
        boolean locked = tryLock(lockKey, waitTime, leaseTime, unit);
        if (!locked) {
            throw new RuntimeException("获取分布式锁失败: " + lockKey);
        }
        try {
            return action.get();
        } finally {
            unlock(lockKey);
        }
    }

    /**
     * 带锁执行（Runnable 无返回值）
     */
    public void runWithLock(String lockKey, long waitTime, long leaseTime,
                             TimeUnit unit, Runnable action) {
        executeWithLock(lockKey, waitTime, leaseTime, unit, () -> {
            action.run();
            return null;
        });
    }

    /**
     * 获取 RLock 对象（灵活使用）
     */
    public RLock getLock(String lockKey) {
        return redissonClient.getLock(lockKey);
    }
}
