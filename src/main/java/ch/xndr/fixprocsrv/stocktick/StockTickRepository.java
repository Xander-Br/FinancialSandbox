package ch.xndr.fixprocsrv.stocktick;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface StockTickRepository extends JpaRepository<StockTick, Long> {

    Optional<StockTick> findFirstById_SymbolOrderById_TimestampDesc(String symbol);


    @Query(value = """
            SELECT
                time_bucket(CAST(:interval AS INTERVAL), ts.timestamp) AS timeBucket,
                ts.symbol                           AS symbol,
                first(ts.price, ts.timestamp)       AS open,
                max(ts.price)                       AS high,
                min(ts.price)                       AS low,
                last(ts.price, ts.timestamp)        AS close,
                sum(ts.volume)                      AS volume
            FROM
                stock_ticks ts
            WHERE
                ts.symbol = :symbol
                AND ts.timestamp >= :startTime
                AND ts.timestamp < :endTime
            GROUP BY
                timeBucket, ts.symbol
            ORDER BY
                timeBucket ASC
            """, nativeQuery = true)
    List<OhlcResult> findOhlcData(
            @Param("symbol") String symbol,
            @Param("startTime") Instant startTime,
            @Param("endTime") Instant endTime,
            @Param("interval") String interval
    );
}
