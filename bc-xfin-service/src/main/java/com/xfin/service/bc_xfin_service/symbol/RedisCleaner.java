package com.xfin.service.bc_xfin_service.symbol;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class RedisCleaner implements CommandLineRunner {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public void run(String... args) {
        redisTemplate.delete("STOCK-LIST");
    }
}