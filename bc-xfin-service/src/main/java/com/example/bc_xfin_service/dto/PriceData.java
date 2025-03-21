package com.example.bc_xfin_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PriceData {
  private String symbol;
  private String marketTime;
  private Double regularMarketPrice;
  private Double regularMarketChangePercent;
  private Double bid;
  private Double ask;
  
}
