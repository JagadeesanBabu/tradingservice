package com.trade.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Entity
@Data
public class TradeOptionsData {
    @Id
    @GeneratedValue
    private UUID id;
    private String symbol;
    private Date tradeDate;
    private Date expiryDate;
    @Enumerated(EnumType.STRING)
    private OptionType optionType;
    private BigDecimal strikePrice;
    private BigDecimal openPrice;
    private BigDecimal closedPrice;
    private BigDecimal highPrice;
    private BigDecimal lowPrice;
    private BigDecimal lastTradedPrice;
    private BigDecimal settlePrice;
    private Integer noOfContracts;
    private BigDecimal turnOver;
    private BigDecimal premiumTurnOver;
    private Integer openInterest;
    private Integer changeInOi;
    private BigDecimal underlyingValue;
}
