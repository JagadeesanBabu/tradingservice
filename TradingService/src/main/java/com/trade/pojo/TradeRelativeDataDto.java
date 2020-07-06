package com.trade.pojo;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
public class TradeRelativeDataDto {
    private BigDecimal relativeStrength;
    private BigDecimal relativeIndex;
    private Date tradeDate;
}
