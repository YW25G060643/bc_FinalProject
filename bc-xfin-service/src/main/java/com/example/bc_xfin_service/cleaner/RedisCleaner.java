package com.example.bc_xfin_service.cleaner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisCleaner implements CommandLineRunner {
    @Autowired private RedisTemplate<String, Object> redisTemplate;
    @Override
    public void run(String... args) {
        redisTemplate.delete("STOCK-LIST");
    }
}
