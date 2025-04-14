package ch.xndr.fixprocsrv.stocktick;

import ch.xndr.fixprocsrv.syntheticmarket.SyntheticMarketEvent;
import ch.xndr.fixprocsrv.syntheticmarket.SyntheticMarketTickDto;
import ch.xndr.fixprocsrv.websocket.SyntheticMarketNotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class StockTickNotificationService {

    @Autowired
    private StockTickService stockTickService;

    private static final Logger log = LoggerFactory.getLogger(SyntheticMarketNotificationService.class);

    @EventListener
    public void handleSyntheticMarketTick(SyntheticMarketEvent event) {
        SyntheticMarketTickDto dto = event.getData();
        StockTick stockTick = new StockTick();
        stockTick.setId(new StockTickId());
        stockTick.getId().setSymbol(dto.getSymbol());
        stockTick.getId().setTimestamp(dto.getTimestamp());
        stockTick.setPrice(dto.getCurrentValue());
        stockTick.setVolume(0L);
        stockTickService.save(stockTick);
    }

}
