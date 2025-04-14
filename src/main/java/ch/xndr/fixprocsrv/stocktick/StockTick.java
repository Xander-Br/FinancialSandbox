package ch.xndr.fixprocsrv.stocktick;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Objects;


@Entity
@Table(name = "stock_ticks")
public class StockTick {

    @EmbeddedId
    private StockTickId id;

    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal price;

    @Column(nullable = false)
    private Long volume;

    public StockTickId getId() {
        return id;
    }

    public void setId(StockTickId id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Long getVolume() {
        return volume;
    }

    public void setVolume(Long volume) {
        this.volume = volume;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StockTick stockTick = (StockTick) o;
        return Objects.equals(id, stockTick.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}