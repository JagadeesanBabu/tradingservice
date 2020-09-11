package com.trade.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class PriceChangeDataDto {
    private BigDecimal closedPrice;
    private BigDecimal priceChangeValue;
    private Date tradeDate;
}
