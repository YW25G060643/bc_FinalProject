package com.bootcamp.bc.bc_yahoo_finance.service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;
import com.bootcamp.bc.bc_yahoo_finance.infra.yahoofinance.dto.YahooQuoteDTO;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface YahooFinanceService {
  YahooQuoteDTO getQuote(List<String> symbols) throws JsonProcessingException;

  public static void main(String[] args) {
    LocalDateTime dateTime =
        LocalDateTime.of(LocalDate.of(2014, 10, 4), LocalTime.of(10, 5));
    System.out.println(dateTime);
    System.out.println(dateTime.toEpochSecond(ZoneOffset.UTC)); // 1412417100

    long epochSecond = dateTime.toEpochSecond(ZoneOffset.UTC);
    LocalDate date = Instant.ofEpochSecond(epochSecond).atZone(ZoneId.systemDefault()).toLocalDate();
    System.out.println(date.getYear());
    System.out.println(date.getMonthValue());
    System.out.println(date.getDayOfMonth());
  }
}
