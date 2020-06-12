package com.trade.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class PriceChangeDataDto {
    private BigDecimal closedPrice;
    private BigDecimal priceChangeValue;
}
