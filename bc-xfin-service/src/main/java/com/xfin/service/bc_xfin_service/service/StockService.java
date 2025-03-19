package com.xfin.service.bc_xfin_service.service;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class StockService {
    @Autowired
    private StockSymbolRepository repository;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public List<String> getStockList() {
        String cached = redisTemplate.opsForValue().get("STOCK-LIST");
        if (cached != null) return parseJson(cached);
        
        List<String> symbols = repository.findAllSymbols();
        String json = buildJson(symbols);
        redisTemplate.opsForValue().set("STOCK-LIST", json, 24, TimeUnit.HOURS);
        return symbols;
    }

    private String buildJson(List<String> symbols) {
        return "{\"STOCK-LIST\": " + new Gson().toJson(symbols) + "}";
    }
}
