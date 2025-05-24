package com.bootcamp.bc.bc_yahoo_finance.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.bootcamp.bc.bc_yahoo_finance.infra.web.ApiResp;
import com.bootcamp.bc.bc_yahoo_finance.infra.web.GlobalExceptionalHandler;
import com.bootcamp.bc.bc_yahoo_finance.infra.yahoofinance.dto.YahooQuoteErrorDTO;
import com.bootcamp.bc.bc_yahoo_finance.infra.yahoofinance.exception.StockQuoteYahooException;

@RestControllerAdvice
public class LocalExceptionHandler extends GlobalExceptionalHandler {
  @ExceptionHandler({StockQuoteYahooException.class})
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public ApiResp<YahooQuoteErrorDTO> businessExceptionHandler(
      StockQuoteYahooException e) {
    return ApiResp.<YahooQuoteErrorDTO>builder() //
        .fail(e) //
        .build();
  }
}
