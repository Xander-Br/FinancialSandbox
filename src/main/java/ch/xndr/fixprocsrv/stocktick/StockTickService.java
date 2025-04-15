package ch.xndr.fixprocsrv.stocktick;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;

@Service
public class StockTickService {

    private static final Logger logger = LoggerFactory.getLogger(StockTickService.class); // Added logger

    @Autowired
    private StockTickRepository stockTickRepository;

    @PersistenceContext
    private EntityManager entityManager;

    private static final Set<String> ALLOWED_INTERVALS = Set.of(
            "1 minute", "2 minutes", "5 minutes", "10 minutes",
            "15 minutes", "30 minutes", "1 hour", "4 hours", "1 day"
    );

    @Transactional
    public void save(StockTick stockTick) {
        stockTickRepository.save(stockTick);
    }

    public Optional<StockTick> getLastTickBySymbol(String symbol) {
        return stockTickRepository.findFirstById_SymbolOrderById_TimestampDesc(symbol);
    }

    @Transactional()
    public List<OhlcResult> getOhlcData(String symbol, Instant startTime, Instant endTime, String interval) {
        // Validate inputs
        if (startTime == null || endTime == null || symbol == null || interval == null) {
            throw new IllegalArgumentException("Symbol, startTime, endTime, and interval parameters cannot be null.");
        }
        if (!endTime.isAfter(startTime)) {
            throw new IllegalArgumentException("endTime must be after startTime.");
        }

        String lowerCaseInterval = interval.toLowerCase(Locale.ROOT).trim();

        if (!ALLOWED_INTERVALS.contains(lowerCaseInterval)) {
            logger.warn("Unsupported interval requested: {}", interval);
            throw new IllegalArgumentException("Unsupported interval: " + interval +
                    ". Allowed intervals include: " + ALLOWED_INTERVALS);
        }

        logger.debug("Fetching OHLC data for symbol '{}' from {} to {} with interval '{}'",
                symbol, startTime, endTime, lowerCaseInterval);

        List<OhlcResult> results = stockTickRepository.findOhlcData(symbol, startTime, endTime, lowerCaseInterval);
        logger.debug("Found {} OHLC buckets for symbol '{}'", results.size(), symbol);
        return results;
    }


}
