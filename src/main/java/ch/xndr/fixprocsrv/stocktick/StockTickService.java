package ch.xndr.fixprocsrv.stocktick;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StockTickService {

    @Autowired
    private StockTickRepository stockTickRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void save(StockTick stockTick) {
        stockTickRepository.save(stockTick);
    }

    public Optional<StockTick> getLastTickBySymbol(String symbol) {
        return stockTickRepository.findFirstById_SymbolOrderById_TimestampDesc(symbol);
    }


}
