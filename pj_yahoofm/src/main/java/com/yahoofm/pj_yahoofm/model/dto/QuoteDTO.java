package com.yahoofm.pj_yahoofm.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class QuoteDTO {
    private String symbol;
    private String shortName;
    private String regularMarketPrice;
    private String currency;
  
}
