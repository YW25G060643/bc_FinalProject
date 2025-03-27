package com.bootcamp.bc.bc_yahoo_finance.service;

import java.util.List;
import com.bootcamp.bc.bc_yahoo_finance.entity.StockEntity;
import com.bootcamp.bc.bc_yahoo_finance.model.Stock;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface StockService {
  List<StockEntity> saveAll(List<StockEntity> entities);
  List<StockEntity> findAll();
  List<Stock> findAllWithCache() throws JsonProcessingException;
  StockEntity findBySymbol(String Symbol);
}
