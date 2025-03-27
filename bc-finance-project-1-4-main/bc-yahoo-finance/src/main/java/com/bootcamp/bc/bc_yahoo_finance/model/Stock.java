package com.bootcamp.bc.bc_yahoo_finance.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Stock {
  private Long id;
  private String symbol;
}
