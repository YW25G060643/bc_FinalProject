package com.example.bc_xfin_service.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tstocks_price")
public class TStocksPrice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String symbol;
    private String type; // SM, D, W, M
    private LocalDateTime apiDatetime;
    private Long regularMarketTime; // Unix timestamp
    private LocalDateTime marketTime; // Converted from regularMarketTime
    private Double regularMarketPrice;
    private Double regularMarketChangePercent;
    private Double bid;
    private Double ask;
    // Getters and Setters
}