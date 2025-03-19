package com.xfin.service.bc_xfin_service.client;

import java.util.List;

import org.apache.hc.client5.http.cookie.BasicCookieStore;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;

public class YahooAPIClient {
  private final RestTemplate restTemplate;

  public YahooAPIClient() {
      this.restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory(
          HttpClientBuilder.create()
              .setDefaultCookieStore(new BasicCookieStore())
              .build()
      ));
  }

  public String getCrumb() {
      restTemplate.getForEntity("https://finance.yahoo.com", String.class);
      return restTemplate.getForObject("https://query1.finance.yahoo.com/v1/test/getcrumb", String.class);
  }

  public QuoteResponse getQuotes(List<String> symbols, String crumb) {
      String url = String.format("https://query1.finance.yahoo.com/v7/finance/quote?symbols=%s&crumb=%s",
          String.join(",", symbols), crumb);
      return restTemplate.getForObject(url, QuoteResponse.class);
  }
}


// StockPriceScheduler.java 定时任务
@Scheduled(cron = "0 */5 * * * *")
public void fetchStockPrices() {
    List<String> symbols = stockService.getStockList();
    String crumb = yahooClient.getCrumb();
    QuoteResponse response = yahooClient.getQuotes(symbols, crumb);
    
    response.getResults().forEach(quote -> {
        StockPrice price = new StockPrice();
        price.setSymbol(quote.getSymbol());
        price.setRegularMarketTime(quote.getRegularMarketTime());
        price.setRegularMarketPrice(quote.getRegularMarketPrice());
        price.setType("5M");
        price.setApiDateTime(LocalDateTime.now());
        priceRepository.save(price);
    });
}