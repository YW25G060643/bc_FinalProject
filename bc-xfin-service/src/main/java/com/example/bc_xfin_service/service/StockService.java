package com.example.bc_xfin_service.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import com.example.bc_xfin_service.entity.TStock;
import com.example.bc_xfin_service.repository.StockRepository;
import com.example.bc_xfin_service.repository.TStockPriceRepository;

@Service
public class StockService {
    @Autowired private StockRepository repo;
    @Autowired private TStockPriceRepository priceRepo;
    @Autowired private RedisTemplate<String, Object> redisTemplate;

    public List<String> getStockList() {
        String key = "STOCK-LIST";
        ValueOperations<String, Object> ops = redisTemplate.opsForValue();
        List<String> symbols = (List<String>) ops.get(key);
        if (symbols == null) {
            symbols = repo.findAll().stream().map(TStock::getSymbol).toList();
            ops.set(key, symbols, java.time.Duration.ofHours(24));
        }
        return symbols;
    }

    public String getSystemDate(String symbol) {
        String key = "SYSDATE-" + symbol;
        String sysDate = (String) redisTemplate.opsForValue().get(key);
        if (sysDate == null) {
            Long maxMarketTimeMillis = priceRepo.findMaxRegularMarketTime(symbol);
            LocalDateTime maxMarketTime = LocalDateTime.ofInstant(
                java.time.Instant.ofEpochMilli(maxMarketTimeMillis), 
                java.time.ZoneId.systemDefault()
            );
            sysDate = maxMarketTime.toLocalDate().toString();
            redisTemplate.opsForValue().set(key, sysDate, Duration.ofHours(8));
        }
        return sysDate;
    }
}