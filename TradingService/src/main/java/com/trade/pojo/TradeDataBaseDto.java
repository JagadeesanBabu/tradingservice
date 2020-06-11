package com.trade.pojo;

import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class TradeDataBaseDto {

    private Date tradeDate;
    private BigDecimal openPrice;
    private BigDecimal closedPrice;
    private BigDecimal highPrice;
    private BigDecimal lowPrice;
    private Integer noOfSharesTraded;
    private BigDecimal turnover;
    private String index;

}
