package com.bootcamp.bc.bc_yahoo_finance.service;

import java.util.List;
import com.bootcamp.bc.bc_yahoo_finance.model.line.Base;
import com.bootcamp.bc.bc_yahoo_finance.model.line.Candle;
import com.bootcamp.bc.bc_yahoo_finance.model.line.PriceLine;
import com.bootcamp.bc.bc_yahoo_finance.model.line.Transaction;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface TransactionService {
  List<Transaction> getTransactionsOnSysdate(String symbol)
      throws JsonProcessingException;

  PriceLine<Base> get5MinBase(String symbol) throws JsonProcessingException;

  PriceLine<Candle> get5MinCandle(String symbol) throws JsonProcessingException;
}
