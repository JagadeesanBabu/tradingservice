package com.trade.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Data
@Entity
public class TradingData {

    @Id
    @GeneratedValue
    private UUID id;

    private Date tradeDate;
    private BigDecimal openPrice;
    private BigDecimal closedPrice;
    private BigDecimal highPrice;
    private BigDecimal lowPrice;
    private Integer noOfSharesTraded;
    private BigDecimal turnover;
    private String index;
}
