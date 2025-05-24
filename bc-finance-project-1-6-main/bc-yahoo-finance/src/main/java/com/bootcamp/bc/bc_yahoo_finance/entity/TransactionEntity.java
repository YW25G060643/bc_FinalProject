package com.bootcamp.bc.bc_yahoo_finance.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tstocks_transaction")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionEntity implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String symbol;
  @Column(name = "tran_type")
  private String tranType;
  @Column(name = "tran_datetime")
  private LocalDateTime tranDatetime;
  @Column(name = "market_unixtime")
  private Long marketUnixTime;
  @Column(name = "market_datetime")
  private LocalDateTime marketDateTime;
  @Column(name = "market_price")
  private Double marketPrice;
  // @Column(name = "market_price_change_percent")
  // private Double marketPriceChangePercent;
  // private Double bid;
  // @Column(name = "bid_size")
  // private Long bidSize;
  // private Double ask;
  // @Column(name = "ask_size")
  // private Long askSize;

  @ManyToOne
  @JoinColumn(name = "stock_id")
  private StockEntity stock;
}
