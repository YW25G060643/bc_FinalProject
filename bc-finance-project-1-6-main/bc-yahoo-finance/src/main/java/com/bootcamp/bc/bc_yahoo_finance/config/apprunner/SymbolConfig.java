package com.bootcamp.bc.bc_yahoo_finance.config.apprunner;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.bootcamp.bc.bc_yahoo_finance.entity.StockEntity;
import com.bootcamp.bc.bc_yahoo_finance.repository.TransactionRepository;
import com.bootcamp.bc.bc_yahoo_finance.service.StockService;
import com.bootcamp.bc.bc_yahoo_finance.repository.StockRepository;

@Component
public class SymbolConfig implements CommandLineRunner {
  @Autowired
  private StockService stockService;
  @Autowired
  private StockRepository stockRepository;
  @Autowired
  private TransactionRepository stockPriceRepository;

  /**
   * For System Design, there should be admin system for stock symbols setup. In
   * case new stock launch in HKEX, our system control the right to support the
   * new symbol.
   * 
   * The admin system should insert, update & delete the stock list. So, this
   * SymbolConfig.class is just for exercise/ testing purpose.
   */
  @Override
  public void run(String... args) {
    // ! Clear all Stock Symbols in DB. (jpa.hibernate.ddl-auto: update)
    // For rerun process.
    this.stockPriceRepository.deleteAll();
    this.stockRepository.deleteAll();

    String[] symbols = new String[] {"0388.HK", "0700.HK", "0005.HK"};
    List<StockEntity> entities = Arrays.stream(symbols) //
        .map(e -> StockEntity.builder().symbol(e).build()) //
        .collect(Collectors.toList());
    stockService.saveAll(entities);
    System.out.println("Insert Stock Symbols Completed.");
  }
}
