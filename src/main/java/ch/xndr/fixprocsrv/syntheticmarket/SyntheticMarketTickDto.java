package ch.xndr.fixprocsrv.syntheticmarket;

import java.math.BigDecimal;
import java.time.Instant;

public class SyntheticMarketTickDto {
    private String symbol;
    private BigDecimal currentValue;
    private Instant timestamp;

    public SyntheticMarketTickDto(String symbol, BigDecimal currentValue) {
        this.symbol = symbol;
        this.currentValue = currentValue;
        this.timestamp = Instant.now();
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public BigDecimal getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(BigDecimal currentValue) {
        this.currentValue = currentValue;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }
}
