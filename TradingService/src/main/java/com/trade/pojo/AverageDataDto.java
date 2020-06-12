package com.trade.pojo;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class AverageDataDto {
    private BigDecimal avgChangeGain;
    private BigDecimal avgChangeLoss;
}
