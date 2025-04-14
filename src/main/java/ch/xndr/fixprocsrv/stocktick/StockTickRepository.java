package ch.xndr.fixprocsrv.stocktick;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StockTickRepository extends JpaRepository<StockTick, Long> {

    Optional<StockTick> findFirstById_SymbolOrderById_TimestampDesc(String symbol);

}
