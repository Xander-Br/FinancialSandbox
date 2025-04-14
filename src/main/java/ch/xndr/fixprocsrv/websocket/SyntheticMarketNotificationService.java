package ch.xndr.fixprocsrv.websocket;


import ch.xndr.fixprocsrv.syntheticmarket.SyntheticMarketEvent;
import ch.xndr.fixprocsrv.syntheticmarket.SyntheticMarketTickDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class SyntheticMarketNotificationService {

    private static final Logger log = LoggerFactory.getLogger(SyntheticMarketNotificationService.class);
    private final SimpMessagingTemplate messagingTemplate;

    public SyntheticMarketNotificationService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @EventListener
    public void handleSyntheticMarketTick(SyntheticMarketEvent event) {
        SyntheticMarketTickDto dto = event.getData();
        String destination = "/topic/synthetic-market-tick";
        log.debug("Sending DTO to WebSocket destination {}: {}", destination, dto);
        messagingTemplate.convertAndSend(destination, dto);
    }

}
