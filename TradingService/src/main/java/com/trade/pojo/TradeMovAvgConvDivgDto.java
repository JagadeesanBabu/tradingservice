package com.trade.pojo;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class TradeMovAvgConvDivgDto {

    private BigDecimal macd;
    private BigDecimal nineMacd;


}
