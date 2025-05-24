package com.bootcamp.bc.bc_yahoo_finance.service.impl;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bootcamp.bc.bc_yahoo_finance.entity.StockEntity;
import com.bootcamp.bc.bc_yahoo_finance.infra.web.GeneralError;
import com.bootcamp.bc.bc_yahoo_finance.infra.web.RedisHelper;
import com.bootcamp.bc.bc_yahoo_finance.infra.yahoofinance.lib.web.BusinessException;
import com.bootcamp.bc.bc_yahoo_finance.mapper.StockMapper;
import com.bootcamp.bc.bc_yahoo_finance.model.Stock;
import com.bootcamp.bc.bc_yahoo_finance.repository.StockRepository;
import com.bootcamp.bc.bc_yahoo_finance.service.StockService;
import com.fasterxml.jackson.core.JsonProcessingException;

@Service
public class StockServiceImpl implements StockService {
  @Autowired
  private StockRepository stockRepository;
  @Autowired
  private RedisHelper redisHelper;
  @Autowired
  private StockMapper stockMapper;

  @Override
  public List<StockEntity> saveAll(List<StockEntity> entities) {
    return this.stockRepository.saveAll(entities);
  }

  @Override
  public List<StockEntity> findAll() {
    return this.stockRepository.findAll();
  }

  @Override
  public StockEntity findBySymbol(String Symbol) {
    return this.stockRepository.findBySymbol(Symbol).orElseThrow(
        () -> new BusinessException(GeneralError.Entity_NOT_FOUND_EX));
  }

  @Override
  public List<Stock> findAllWithCache() throws JsonProcessingException {
    // read from Redis ...
    Stock[] redisStocks = this.redisHelper.get("stock-list", Stock[].class);
    if (redisStocks == null) {
      // read from DB ...
      List<Stock> dbStocks = this.stockRepository.findAll().stream() //
          .map(s -> this.stockMapper.map(s)) //
          .collect(Collectors.toList());
      // write to Redis ...
      this.redisHelper.set("stock-list", dbStocks, Duration.ofMinutes(10));
      return dbStocks;
    }
    return Arrays.asList(redisStocks);
  }
}
