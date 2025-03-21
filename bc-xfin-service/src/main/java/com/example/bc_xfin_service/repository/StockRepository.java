package com.example.bc_xfin_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.bc_xfin_service.entity.TStock;

public interface StockRepository extends JpaRepository<TStock, Long> {
    boolean existsBySymbol(String symbol);
}