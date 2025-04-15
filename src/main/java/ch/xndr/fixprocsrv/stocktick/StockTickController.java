package ch.xndr.fixprocsrv.stocktick; // Use your actual package

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Import the projection interface
import ch.xndr.fixprocsrv.stocktick.OhlcResult;


import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/stocks")
public class StockTickController {

    private static final Logger logger = LoggerFactory.getLogger(StockTickController.class);

    @Autowired
    private StockTickService stockTickService;

    /**
     * REST endpoint to retrieve OHLCV data for a given stock symbol.
     *
     * Example Usage:
     * GET /api/stocks/AAPL/ohlc?interval=5%20minutes&startTime=2025-04-15T10:00:00Z&endTime=2025-04-15T12:00:00Z
     * GET /api/stocks/GOOG/ohlc?interval=1%20hour // Uses default time range (last 24h)
     *
     * @param symbol    The stock symbol (path variable).
     * @param interval  The time bucket interval (e.g., "1 minute", "5 minutes", "1 hour"). Required.
     * @param startTime The start of the time range (ISO 8601 format, e.g., yyyy-MM-dd'T'HH:mm:ss'Z'). Optional.
     * @param endTime   The end of the time range (ISO 8601 format). Optional.
     * @return A ResponseEntity containing a list of OHLC data or an error message.
     */
    @GetMapping("/{symbol}/ohlc")
    public ResponseEntity<?> getOhlcDataApi( // Use ResponseEntity<?> for flexible success/error responses
                                             @PathVariable String symbol,
                                             @RequestParam String interval,
                                             @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant startTime,
                                             @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant endTime) {

        logger.info("Received OHLC request for symbol: {}, interval: {}, startTime: {}, endTime: {}",
                symbol, interval, startTime, endTime);

        if (symbol == null || symbol.isBlank()) {
            return ResponseEntity.badRequest().body("Error: Symbol path variable cannot be empty.");
        }
        if (interval == null || interval.isBlank()) {
            return ResponseEntity.badRequest().body("Error: Interval query parameter is required.");
        }

        if (endTime == null) {
            endTime = Instant.now();
        }
        if (startTime == null) {
            startTime = endTime.minus(Duration.ofDays(1));
        }


        if (startTime.isAfter(endTime)) {
            logger.warn("Invalid time range requested for symbol {}: startTime ({}) is after endTime ({})",
                    symbol, startTime, endTime);
            return ResponseEntity.badRequest().body("Error: startTime must be before endTime.");
        }


        try {
            // Call the service layer which contains business logic and repository access
            List<OhlcResult> ohlcData = stockTickService.getOhlcData(symbol.toUpperCase(), startTime, endTime, interval); // Standardize symbol case if needed

            return ResponseEntity.ok(ohlcData);

        } catch (IllegalArgumentException e) {
            logger.warn("Bad request for OHLC data for symbol {}: {}", symbol, e.getMessage());
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Unexpected error fetching OHLC data for symbol {}", symbol, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred while processing your request.");
        }
    }

    @GetMapping("/{symbol}/last")
    public ResponseEntity<?> getLastTickApi(@PathVariable String symbol) {
        if (symbol == null || symbol.isBlank()) {
            return ResponseEntity.badRequest().body("Error: Symbol path variable cannot be empty.");
        }
        try {
            Optional<StockTick> lastTick = stockTickService.getLastTickBySymbol(symbol.toUpperCase());
            return ResponseEntity.ok(lastTick.orElse(null));
        } catch (Exception e) {
            logger.error("Unexpected error fetching last tick for symbol {}", symbol, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred while processing your request.");
        }
    }

}
