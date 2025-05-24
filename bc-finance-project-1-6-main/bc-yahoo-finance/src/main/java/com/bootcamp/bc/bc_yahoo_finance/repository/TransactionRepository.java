package com.bootcamp.bc.bc_yahoo_finance.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.bootcamp.bc.bc_yahoo_finance.entity.TransactionEntity;
import io.lettuce.core.dynamic.annotation.Param;

@Repository
public interface TransactionRepository
    extends JpaRepository<TransactionEntity, Long> {

  @Query(
      value = "SELECT p FROM TransactionEntity p WHERE p.symbol = :symbol AND p.marketUnixTime BETWEEN :startEpoch AND :endEpoch")
  List<TransactionEntity> findBySymbol(String symbol, long startEpoch,
      long endEpoch);

  @Query(
      value = "SELECT max(p.marketUnixTime) FROM TransactionEntity p WHERE p.symbol = :symbol")
  Long getMaxMarketTime(@Param("symbol") String symbol);
}
