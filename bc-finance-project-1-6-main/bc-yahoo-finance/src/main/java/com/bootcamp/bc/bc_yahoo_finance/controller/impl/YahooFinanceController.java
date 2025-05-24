package com.bootcamp.bc.bc_yahoo_finance.controller.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.bootcamp.bc.bc_yahoo_finance.controller.YahooFinanceOperation;
import com.bootcamp.bc.bc_yahoo_finance.infra.yahoofinance.dto.YahooQuoteDTO;
import com.bootcamp.bc.bc_yahoo_finance.service.YahooFinanceService;
import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
public class YahooFinanceController implements YahooFinanceOperation {

  @Autowired
  private YahooFinanceService yahooFinanceService;

  @Override
  public YahooQuoteDTO getQuote(List<String> symbols) throws JsonProcessingException {
    return yahooFinanceService.getQuote(symbols);
  }
}
