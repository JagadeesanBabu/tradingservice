package com.trade.pojo;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
public class TradeMovAvgConvDivgDto {

    private BigDecimal macd;
    private BigDecimal nineMacd;
    private Date tradeDate;


}
