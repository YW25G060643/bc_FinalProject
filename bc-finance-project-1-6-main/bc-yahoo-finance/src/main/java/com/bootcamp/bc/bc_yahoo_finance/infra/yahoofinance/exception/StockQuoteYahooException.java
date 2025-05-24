package com.bootcamp.bc.bc_yahoo_finance.infra.yahoofinance.exception;

import com.bootcamp.bc.bc_yahoo_finance.infra.yahoofinance.dto.YahooQuoteErrorDTO;
import com.bootcamp.bc.bc_yahoo_finance.infra.yahoofinance.lib.web.BusinessException;

public class StockQuoteYahooException extends BusinessException {
  public StockQuoteYahooException(YahooQuoteErrorDTO quoteErrorDTO) {
    super(YahooFinanceError.REST_CLIENT_EX,
        quoteErrorDTO.getBody().getError().getMessage());
  }
}
