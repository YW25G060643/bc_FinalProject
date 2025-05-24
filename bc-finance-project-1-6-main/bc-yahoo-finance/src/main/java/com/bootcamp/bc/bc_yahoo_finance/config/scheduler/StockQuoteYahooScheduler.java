package com.bootcamp.bc.bc_yahoo_finance.config.scheduler;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.bootcamp.bc.bc_yahoo_finance.entity.StockEntity;
import com.bootcamp.bc.bc_yahoo_finance.entity.TransactionEntity;
import com.bootcamp.bc.bc_yahoo_finance.infra.yahoofinance.dto.YahooQuoteDTO;
import com.bootcamp.bc.bc_yahoo_finance.mapper.TransactionMapper;
import com.bootcamp.bc.bc_yahoo_finance.model.Stock;
import com.bootcamp.bc.bc_yahoo_finance.repository.TransactionRepository;
import com.bootcamp.bc.bc_yahoo_finance.service.StockService;
import com.bootcamp.bc.bc_yahoo_finance.service.YahooFinanceService;
import com.fasterxml.jackson.core.JsonProcessingException;

@Component
public class StockQuoteYahooScheduler {
  @Autowired
  private YahooFinanceService yahooFinanceService;
  @Autowired
  private TransactionMapper transactionMapper;
  @Autowired
  private StockService stockService;
  @Autowired
  private TransactionRepository transactionRepository;

  // ! 100_000 = 100 seconds
  // ! 300_000 = 5 mins
  @Scheduled(fixedRate = 300_000)
  // @Scheduled(cron = "0 */5 9-16 * * MON-FRI")
  public void stockQuote() throws JsonProcessingException {
    List<Stock> stocks = this.stockService.findAllWithCache();
    System.out.println("stocks" + stocks);
    if (stocks == null || stocks.size() == 0)
      return;
    // ! Get All Symbols from DB (Cache Pattern)
    List<String> symbols = stocks.stream() //
        .map(s -> s.getSymbol()) //
        .collect(Collectors.toList());
    // ! Get Price from Yahoo Finance (Direct Web Call)
    // Insert to tstocks_price one by one
    YahooQuoteDTO quoteDTO = this.yahooFinanceService.getQuote(symbols);
    quoteDTO.getBody().getResults().forEach(s -> {
      StockEntity stockEntity = this.stockService.findBySymbol(s.getSymbol());
      TransactionEntity transactionEntity = this.transactionMapper.map(s);
      transactionEntity.setStock(stockEntity);
      transactionEntity.setSymbol(stockEntity.getSymbol());
      transactionEntity.setTranType("5MIN");
      this.transactionRepository.save(transactionEntity);
    });

    // ! Test for Task 6a (Get Sysdate by symbol)
    // for (String symbol : symbols) {
    //   System.out.println(
    //       "symbol=" + symbol + ", sysdate=" + systemService.getSysDate(symbol));
    // }
  }
}
