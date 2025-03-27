package com.bootcamp.bc.bc_yahoo_finance.infra.web;

import java.util.List;

public class ApiResp<T> {
  private String code;
  private String message;
  private List<T> data;

  public String code(String code) {
    return this.code;
  }

  public String message(String message) {
    return this.message;
  }

  public List<T> getData() {
    return this.data;
  }

  public static <T> Builder<T> builder() {
    return new Builder<>();
  }

  private ApiResp(Builder<T> builder) {
    this.code = builder.code;
    this.message = builder.message;
    this.data = builder.data;
  }

  public static class Builder<T> {
    private String code;
    private String message;
    private List<T> data;

    public Builder<T> ok() {
      this.code = SysCode.OK.getCode();
      this.message = SysCode.OK.getMessage();
      return this;
    }

    public Builder<T> fail(Exception e) {
      this.code = SysCode.FAIL.getCode();
      this.message = e.getMessage();
      return this;
    }

    public Builder<T> data(List<T> data) {
      this.data = data;
      return this;
    }

    public ApiResp<T> build() {
      return new ApiResp<>(this);
    }
  }
}
