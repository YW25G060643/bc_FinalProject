package com.bootcamp.bc.bc_yahoo_finance.infra.web;

import com.bootcamp.bc.bc_yahoo_finance.infra.yahoofinance.lib.web.Errorable;

public enum GeneralError implements Errorable {
  Entity_NOT_FOUND_EX(1, "Entity not found."), //
  INV_INPUT_EX(2, "Invalid Input."), //
  REST_CLIENT_EX(3, "Rest Client Exception."),;

  private int code;
  private String message;

  private GeneralError(int code, String message) {
    this.code = code;
    this.message = message;
  }

  @Override
  public int getCode() {
    return this.code;
  }

  @Override
  public String getMessage() {
    return this.message;
  }
}
