package com.trade.jpa;

import com.trade.entity.TradingData;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface TradeDataRepository extends JpaRepository<TradingData, UUID> {
    List<TradingData> findAllByIndex(String index, Pageable page);
    List<TradingData> findAllByTradeDateBetweenAndIndex(Date tradeDateStartDate, Date tradeDateEndDate, String index, Pageable page);
}