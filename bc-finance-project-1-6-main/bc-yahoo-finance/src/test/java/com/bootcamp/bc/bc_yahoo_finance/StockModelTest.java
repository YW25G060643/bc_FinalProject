package com.bootcamp.bc.bc_yahoo_finance;

import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.hamcrest.MatcherAssert.assertThat;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import com.bootcamp.bc.bc_yahoo_finance.model.line.Base;
import com.bootcamp.bc.bc_yahoo_finance.model.line.PricePoint;

public class StockModelTest {
  @Test
  void testStockModel() {
    LocalDateTime datetime = LocalDateTime.now();
    PricePoint<Base> point = new PricePoint<>(datetime, new Base(1.2));
    PricePoint<Base> point2 = new PricePoint<>(datetime, new Base(1.2));
    assertEquals(point, point2);
  }
}
