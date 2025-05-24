package com.bootcamp.bc.bc_yahoo_finance.model.line;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import com.bootcamp.bc.bc_yahoo_finance.model.line.PriceLine.LineType;

public class PriceLineProcessor {
  private PriceLine.LineType lineType;
  private Long stockId;
  private String symbol;
  private List<Transaction> transactions;

  public static PriceLineProcessor of(PriceLine.LineType lineType, Long stockId,
      String symbol, List<Transaction> transactions) {
    return new PriceLineProcessor(lineType, stockId, symbol, transactions);
  }

  private PriceLineProcessor(PriceLine.LineType lineType, Long stockId,
      String symbol, List<Transaction> transactions) {
    this.lineType = lineType;
    this.stockId = stockId;
    this.symbol = symbol;
    this.transactions = transactions;
  }

  public PriceLine<Base> getBaseLine(int minutePerInterval) {
    List<Transaction> transactions = prepareTransactions(minutePerInterval);
    
    Set<PricePoint<Base>> points = transactions.stream() //
        .map(t -> new PricePoint<>(t.getDateTime(), new Base(t.getPrice()))) //
        .collect(Collectors.toSet());
        System.out.println("points=" + points);
    return PriceLine.<Base>builder() //
        .lineType(PriceLine.LineType.MIN5) //
        .symbol(this.symbol) //
        .stockId(this.stockId) //
        .points(points) //
        .build();
  }

  public PriceLine<Candle> getCandleLine(int minutePerInterval) {
    List<Transaction> transactions = prepareTransactions(minutePerInterval);

    Set<PricePoint<Candle>> points = transactions.stream() //
        .map(t -> new PricePoint<>(t.getDateTime(),
            new Candle(1.0, 1.0, 1.0, 1.0))) // TBC.
        .collect(Collectors.toSet());

    return PriceLine.<Candle>builder() //
        .lineType(PriceLine.LineType.MIN5) //
        .symbol(this.symbol) //
        .stockId(this.stockId) //
        .points(points) //
        .build();
  }

  private List<Transaction> prepareTransactions(int minutePerInterval) {
    // ! Step 1: Group transactions into 5-minute intervals
    Map<LocalDateTime, List<Transaction>> groupedBy5Min =
        this.transactions.stream().collect(Collectors.groupingBy(
            t -> roundUpForInterval(minutePerInterval, t.getDateTime())));

    // ! Step 2: Pick the last transaction for each 5-minute interval
    List<Transaction> transactions = new ArrayList<>();
    for (Map.Entry<LocalDateTime, List<Transaction>> entry : groupedBy5Min
        .entrySet()) {
      Transaction lastTransaction = entry.getValue().stream()
          .max((t1, t2) -> t1.getDateTime().compareTo(t2.getDateTime()))
          .orElse(null);
      if (lastTransaction != null) {
        lastTransaction.setDateTime(entry.getKey());
        transactions.add(lastTransaction);
      }
    }

    // ! Step 3: Sort the list by timestamp, return list
    Comparator<Transaction> sortbyTimestamp =
        (t1, t2) -> t1.getDateTime().compareTo(t2.getDateTime());
    transactions = transactions.stream() //
        .sorted(sortbyTimestamp) //
        .collect(Collectors.toList());

    // ! Step 4: Fill the empty intervals, return list
    int size = transactions.size();
    for (int i = 0; i < size - 1; i++) {
      LocalDateTime head = transactions.get(i).getDateTime();
      LocalDateTime tail = transactions.get(i + 1).getDateTime();
      while (head.isBefore(tail)) {
        LocalDateTime newTime = head.plusMinutes(minutePerInterval);
        Transaction newAdd =
            new Transaction(newTime, transactions.get(i).getPrice());
        transactions.add(newAdd);
        head = newTime;
      }
    }
    return transactions;
  }


  private static LocalDateTime roundUpForInterval(int minutePerInterval,
      LocalDateTime timestamp) {
    return timestamp.truncatedTo(ChronoUnit.HOURS).plusMinutes(
        (timestamp.getMinute() / minutePerInterval + 1) * minutePerInterval);
  }

  public static void main(String[] args) {
    // Example usage
    List<Transaction> transactions = List.of( //
        new Transaction(LocalDateTime.of(2024, 11, 6, 10, 3), 100.5),
        new Transaction(LocalDateTime.of(2024, 11, 6, 10, 7), 101.0),
        new Transaction(LocalDateTime.of(2024, 11, 6, 10, 8), 102.5),
        new Transaction(LocalDateTime.of(2024, 11, 6, 10, 23), 97.5),
        new Transaction(LocalDateTime.of(2024, 11, 6, 10, 29), 92.5),
        new Transaction(LocalDateTime.of(2024, 11, 6, 10, 28), 99.5) //
    );

    PriceLine<Base> priceLine = PriceLineProcessor
        .of(LineType.MIN5, 1L, "0388.HK", transactions).getBaseLine(5);

    priceLine.getPoints().forEach(point -> {
      System.out.println(
          "Time: " + point.getDateTime() + ", Price: " + point.getPrice());
    });
  }
}
