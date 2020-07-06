package com.trade.pojo;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
public class AverageDataDto {
    private BigDecimal avgChangeGain;
    private BigDecimal avgChangeLoss;
    private Date tradeDate;
}
