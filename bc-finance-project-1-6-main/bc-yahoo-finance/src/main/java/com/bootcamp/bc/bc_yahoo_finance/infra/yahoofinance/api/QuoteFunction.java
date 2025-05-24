package com.bootcamp.bc.bc_yahoo_finance.infra.yahoofinance.api;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import com.bootcamp.bc.bc_yahoo_finance.infra.yahoofinance.dto.YahooQuoteDTO;
import com.bootcamp.bc.bc_yahoo_finance.infra.yahoofinance.dto.YahooQuoteErrorDTO;
import com.bootcamp.bc.bc_yahoo_finance.infra.yahoofinance.exception.StockQuoteYahooException;
import com.bootcamp.bc.bc_yahoo_finance.infra.yahoofinance.util.Yahoo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public interface QuoteFunction {
  RestTemplate getRestTemplate();

  String getCrumbKey();

  // https://query1.finance.yahoo.com/v7/finance/quote?symbols=MSFT,AAPL&crumb=wH.alLk3BaV
  default YahooQuoteDTO getQuote(List<String> symbols)
      throws JsonProcessingException {
    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
    params.put("symbols", List.of(String.join(",", symbols)));

    String crumbKey = this.getCrumbKey();
    params.put("crumb", List.of(crumbKey));

    String url = UriComponentsBuilder.newInstance() //
        .scheme("https") //
        .host(Yahoo.DOMAIN) //
        .path(Yahoo.VERSION_QUOTE) //
        .path(Yahoo.ENDPOINT_QUOTE) //
        .queryParams(params) //
        .toUriString();

    ResponseEntity<String> response =
        this.getRestTemplate().getForEntity(url, String.class);
    if (!response.getStatusCode().equals(HttpStatus.OK)) {
      YahooQuoteErrorDTO errorDto = new ObjectMapper()
          .readValue(response.getBody(), YahooQuoteErrorDTO.class);
      throw new StockQuoteYahooException(errorDto);
    }
    YahooQuoteDTO yahooQuoteDTO =
        new ObjectMapper().readValue(response.getBody(), YahooQuoteDTO.class);
    System.out.println(yahooQuoteDTO);
    return yahooQuoteDTO;
  }
}
