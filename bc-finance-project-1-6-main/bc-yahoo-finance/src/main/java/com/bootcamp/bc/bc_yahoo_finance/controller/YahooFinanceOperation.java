package com.bootcamp.bc.bc_yahoo_finance.controller;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.bootcamp.bc.bc_yahoo_finance.infra.yahoofinance.dto.YahooQuoteDTO;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface YahooFinanceOperation {
  // http://localhost:8100/v1/yahoo/quote?symbols=0388.HK,0700.HK
  @GetMapping(value = "/yahoo/quote")
  public YahooQuoteDTO getQuote(@RequestParam List<String> symbols)
      throws JsonProcessingException;
}
