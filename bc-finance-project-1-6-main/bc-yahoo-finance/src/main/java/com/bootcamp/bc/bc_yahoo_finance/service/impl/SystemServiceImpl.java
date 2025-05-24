package com.bootcamp.bc.bc_yahoo_finance.service.impl;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bootcamp.bc.bc_yahoo_finance.exception.LocalError;
import com.bootcamp.bc.bc_yahoo_finance.infra.web.RedisHelper;
import com.bootcamp.bc.bc_yahoo_finance.infra.yahoofinance.lib.web.BusinessException;
import com.bootcamp.bc.bc_yahoo_finance.repository.TransactionRepository;
import com.bootcamp.bc.bc_yahoo_finance.service.SystemService;
import com.fasterxml.jackson.core.JsonProcessingException;

@Service
public class SystemServiceImpl implements SystemService {
  @Autowired
  private RedisHelper redisHelper;
  @Autowired
  private TransactionRepository stockPriceRepository;

  @Override
  public LocalDate getSysDate(String symbol) throws JsonProcessingException {
    String redisKey = "SYSDATE-".concat(symbol);
    String redisSysDate = this.redisHelper.get(redisKey, String.class);
    DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMdd");
    if (redisSysDate == null) {
      Long lastMarketTime = stockPriceRepository.getMaxMarketTime(symbol);
      // System.out.println("lastMarketTime=" + lastMarketTime);
      if (lastMarketTime == null) {
        throw new BusinessException(LocalError.SYSDATE_NOT_READY,
            "Sysdate is not ready for " + symbol + ".");
      }
      LocalDate dbSysDate = Instant.ofEpochSecond(lastMarketTime.longValue()) //
          .atZone(ZoneId.systemDefault()) //
          .toLocalDate();
      this.redisHelper.set(redisKey, dbSysDate.format(format),
          Duration.ofMinutes(5));
      return dbSysDate;
    }
    return LocalDate.parse(redisSysDate, format);
  }
}
