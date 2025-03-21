package com.example.bc_xfin_service.scheduler;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CacheScheduler implements org.springframework.boot.CommandLineRunner {
    @Autowired private RedisTemplate<String, Object> redisTemplate;

    // 每天08:55清除缓存
    @Scheduled(cron = "0 55 8 * * *")
    public void clearCacheDaily() {
        Set<String> sysDateKeys = redisTemplate.keys("SYSDATE-*");
        redisTemplate.delete(sysDateKeys);
        Set<String> fiveMinKeys = redisTemplate.keys("5MIN-*");
        redisTemplate.delete(fiveMinKeys);
    }

    // 服务启动时清除缓存
    @Override
    public void run(String... args) {
        clearCacheDaily();
    }
}
