package com.bootcamp.bc.bc_yahoo_finance.infra.web;

import java.util.ArrayList;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import com.bootcamp.bc.bc_yahoo_finance.infra.yahoofinance.lib.web.BusinessException;

public class GlobalExceptionalHandler {

  @ExceptionHandler({MissingServletRequestParameterException.class})
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public ApiResp<Object> missingRequestParamterExceptionHandler(
      MissingServletRequestParameterException e) {
    return ApiResp.<Object>builder() //
        .fail(e) //
        .data(new ArrayList<>()) //
        .build();
  }

  @ExceptionHandler({BusinessException.class})
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public ApiResp<Object> businessExceptionHandler(BusinessException e) {
    return ApiResp.<Object>builder() //
        .fail(e) //
        .data(new ArrayList<>()) //
        .build();
  }

  @ExceptionHandler({Exception.class})
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public ApiResp<Object> unhandledExceptionHandler(Exception e) {
    return ApiResp.<Object>builder() //
        .fail(e) //
        .data(new ArrayList<>()) //
        .build();
  }
}
