package com.bootcamp.bc.bc_yahoo_finance.mapper;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import org.springframework.stereotype.Component;
import com.bootcamp.bc.bc_yahoo_finance.entity.TransactionEntity;
import com.bootcamp.bc.bc_yahoo_finance.infra.yahoofinance.dto.YahooQuoteDTO;

@Component
public class TransactionMapper {
  public TransactionEntity map(YahooQuoteDTO.QuoteBody.QuoteResult quotePrice) {
    LocalDateTime quoteDateTime = LocalDateTime.ofInstant(
        Instant.ofEpochSecond(quotePrice.getRegularMarketTime()),
        ZoneId.systemDefault());
    return TransactionEntity.builder().symbol(quotePrice.getSymbol())
        .tranDatetime(LocalDateTime.now()) //
        .marketUnixTime(quotePrice.getRegularMarketTime()) //
        .marketDateTime(quoteDateTime) //
        .marketPrice(quotePrice.getRegularMarketPrice()) //
        // .marketPriceChangePercent(quotePrice.getRegularMarketChangePercent()) //
        // .ask(quotePrice.getAsk()) //
        // .askSize(quotePrice.getAskSize()) //
        // .bid(quotePrice.getBid()) //
        // .bidSize(quotePrice.getBidSize()) //
        .build();
  }
}
