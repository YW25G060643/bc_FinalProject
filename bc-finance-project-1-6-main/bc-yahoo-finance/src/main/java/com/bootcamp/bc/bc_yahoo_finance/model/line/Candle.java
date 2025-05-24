package com.bootcamp.bc.bc_yahoo_finance.model.line;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class Candle extends PriceType {
  private double open;
  private double close;
  private double high;
  private double low;
}
