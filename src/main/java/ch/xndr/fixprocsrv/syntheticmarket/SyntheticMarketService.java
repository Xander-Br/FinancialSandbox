package ch.xndr.fixprocsrv.syntheticmarket;

import ch.xndr.fixprocsrv.stocktick.StockTick;
import ch.xndr.fixprocsrv.stocktick.StockTickService;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class SyntheticMarketService {

    private final ApplicationEventPublisher eventPublisher;

    private static final Logger log = LoggerFactory.getLogger(SyntheticMarketService.class);

    @Autowired
    private StockTickService stockTickService;
    private GMBSyntheticMarket gmbSyntheticMarket;

    public SyntheticMarketService(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @PostConstruct
    public void init() {
        Optional<StockTick> lastTick = stockTickService.getLastTickBySymbol("AAPL");
        BigDecimal lastPrice = BigDecimal.valueOf(100);
        if (lastTick.isPresent()) {
            lastPrice = lastTick.get().getPrice();
        }
        this.gmbSyntheticMarket = new GMBSyntheticMarket("AAPL", 1.0, 1.0, 1.0 / (365.0*24*60*60), lastPrice);

    }


    @Scheduled(initialDelay = 1000, fixedDelay = 1000)
    public void tick() {

        BigDecimal generatedValue = this.gmbSyntheticMarket.generateNextValue();
        log.info("Generated Value: {}", generatedValue);
        SyntheticMarketTickDto syntheticMarketTickDto = new SyntheticMarketTickDto(this.gmbSyntheticMarket.symbol, generatedValue);
        eventPublisher.publishEvent(new SyntheticMarketEvent(syntheticMarketTickDto));
    }

}
