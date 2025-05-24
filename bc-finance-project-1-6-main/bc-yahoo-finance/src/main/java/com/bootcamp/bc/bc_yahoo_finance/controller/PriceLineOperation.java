package com.bootcamp.bc.bc_yahoo_finance.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.bootcamp.bc.bc_yahoo_finance.infra.web.ApiResp;
import com.bootcamp.bc.bc_yahoo_finance.model.line.Base;
import com.bootcamp.bc.bc_yahoo_finance.model.line.Candle;
import com.bootcamp.bc.bc_yahoo_finance.model.line.PriceLine;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface PriceLineOperation {
  @GetMapping("/priceline/base")
  ApiResp<PriceLine<Base>> getBasePrices(@RequestParam String symbol)
      throws JsonProcessingException;

  @GetMapping("/priceline/candle")
  ApiResp<PriceLine<Candle>> getCandlePrices(@RequestParam String symbol)
      throws JsonProcessingException;
}
