package ch.xndr.fixprocsrv.stocktick;

import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

// 1. Create the Embeddable ID class
@Embeddable
public class StockTickId implements Serializable { // Must implement Serializable

    @Column(nullable = false, columnDefinition = "TIMESTAMPTZ")
    private Instant timestamp;

    @Column(nullable = false, length = 10)
    private String symbol;

    public Instant getTimestamp() {
        return timestamp;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StockTickId that = (StockTickId) o;
        return Objects.equals(timestamp, that.timestamp) && Objects.equals(symbol, that.symbol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timestamp, symbol);
    }
}