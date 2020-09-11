package com.trade.jpa;

import com.trade.entity.TradeOptionsData;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TradeDataOptionsRepository extends JpaRepository<TradeOptionsData, UUID> {
    List<TradeOptionsData> findAllBySymbol(String symbol, Pageable page);

}
