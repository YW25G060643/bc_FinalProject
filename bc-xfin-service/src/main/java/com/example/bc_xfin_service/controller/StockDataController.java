package com.example.bc_xfin_service.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import com.example.bc_xfin_service.dto.PriceData;
import com.example.bc_xfin_service.entity.TStocksPrice;
import com.example.bc_xfin_service.repository.TStockPriceRepository;
import com.example.bc_xfin_service.service.CacheService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/stock")
public class StockDataController {
    @Autowired private CacheService cacheService;
    @Autowired private TStockPriceRepository priceRepo;
    @Autowired private RedisTemplate<String, Object> redisTemplate;

    @GetMapping("/{symbol}/5min")
    public List<PriceData> get5MinData(@PathVariable String symbol) {
        String sysDate = cacheService.getSystemDate(symbol);
        String redisKey = "5MIN-" + symbol;
        List<PriceData> cachedData = (List<PriceData>) redisTemplate.opsForValue().get(redisKey);
        if (cachedData == null) {
            cachedData = priceRepo.findBySymbolAndTypeAndDate(symbol, "SM", sysDate);
            redisTemplate.opsForValue().set(redisKey, cachedData, Duration.ofHours(12));
        }
        // 检查数据库是否有更新（示例逻辑）
        LocalDateTime latestDbTime = priceRepo.findMaxRegularMarketTime(symbol);
        if (latestDbTime.isAfter(cachedData.get(0).getMarketTime())) {
            cachedData = priceRepo.findBySymbolAndTypeAndDate(symbol, "SM", sysDate);
            redisTemplate.opsForValue().set(redisKey, cachedData, Duration.ofHours(12));
        }
        return cachedData;
    }
    
}
