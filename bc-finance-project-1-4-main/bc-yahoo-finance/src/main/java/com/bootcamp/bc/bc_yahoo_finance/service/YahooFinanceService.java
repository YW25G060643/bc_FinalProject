package com.bootcamp.bc.bc_yahoo_finance.service;

import java.util.List;
import com.bootcamp.bc.bc_yahoo_finance.infra.yahoofinance.dto.YahooQuoteDTO;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface YahooFinanceService {
  YahooQuoteDTO getQuote(List<String> symbols) throws JsonProcessingException;
}
