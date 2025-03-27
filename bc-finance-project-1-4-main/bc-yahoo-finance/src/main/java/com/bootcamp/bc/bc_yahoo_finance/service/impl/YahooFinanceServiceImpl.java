package com.bootcamp.bc.bc_yahoo_finance.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bootcamp.bc.bc_yahoo_finance.infra.yahoofinance.client.YahooFinanceClient;
import com.bootcamp.bc.bc_yahoo_finance.infra.yahoofinance.dto.YahooQuoteDTO;
import com.bootcamp.bc.bc_yahoo_finance.service.YahooFinanceService;
import com.fasterxml.jackson.core.JsonProcessingException;

@Service
public class YahooFinanceServiceImpl implements YahooFinanceService {
  @Autowired
  private YahooFinanceClient yahooFinanceClient;

  @Override
  public YahooQuoteDTO getQuote(List<String> symbols) throws JsonProcessingException {
    return yahooFinanceClient.getQuote(symbols);
  }
}
