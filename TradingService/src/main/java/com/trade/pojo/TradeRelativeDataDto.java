package com.trade.pojo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TradeRelativeDataDto extends TradeDataBaseDto {
    private BigDecimal priceChange;
    private BigDecimal avgChangeGain;
    private BigDecimal avgChangeLoss;
    private BigDecimal relativeStrength;
    private BigDecimal relativeIndex;
}
