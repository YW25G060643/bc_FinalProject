package com.yahoofm.pj_yahoofm.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
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
    private String language;
    private String region;  
    private String marketState;
    private String exchange;
    private String quoteType;
    private String typeDisp;

}
