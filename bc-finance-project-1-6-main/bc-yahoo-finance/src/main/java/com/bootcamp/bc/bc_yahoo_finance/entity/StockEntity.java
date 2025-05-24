package com.bootcamp.bc.bc_yahoo_finance.entity;

import java.io.Serializable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tstocks")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StockEntity implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String symbol;

  // @Builder.Default
  // @OneToMany(mappedBy = "stock",
  //     cascade = {CascadeType.PERSIST, CascadeType.MERGE},
  //     fetch = FetchType.LAZY)
  // private List<StockQuoteYahooEntity> stockQuotes = new ArrayList<>();
}
