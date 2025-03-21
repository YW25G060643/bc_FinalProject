package com.example.bc_xfin_service.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.bc_xfin_service.dto.PriceData;
import com.example.bc_xfin_service.entity.TStock;
import com.example.bc_xfin_service.entity.TStocksPrice;

public interface TStockPriceRepository extends JpaRepository<TStock, Long> {
    boolean existsBySymbol(String symbol);
    @Query("SELECT MAX(t.regularMarketTime) FROM TStocksPrice t WHERE t.symbol = ?1")
    Long findMaxRegularMarketTime(String symbol);

    @Query("SELECT t FROM TStocksPrice t WHERE t.symbol = ?1 AND t.type = ?2 AND DATE(t.marketTime) = ?3")
    List<PriceData> findBySymbolAndTypeAndDate(String symbol, String type, String date);
}