package ch.xndr.fixprocsrv.stocktick; // Use your package

import java.math.BigDecimal;
import java.time.Instant;


public interface OhlcResult {
    Instant getTimeBucket(); // Alias in SQL: timebucket
    String getSymbol();      // Alias in SQL: symbol
    BigDecimal getOpen();    // Alias in SQL: open
    BigDecimal getHigh();    // Alias in SQL: high
    BigDecimal getLow();     // Alias in SQL: low
    BigDecimal getClose();   // Alias in SQL: close
    Long getVolume();        // Alias in SQL: volume
}

