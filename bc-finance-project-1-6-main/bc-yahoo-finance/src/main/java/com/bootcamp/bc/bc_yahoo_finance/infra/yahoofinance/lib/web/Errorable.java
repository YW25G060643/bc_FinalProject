package com.bootcamp.bc.bc_yahoo_finance.infra.yahoofinance.lib.web;

public interface Errorable {
  int getCode();
  String getMessage();
}
