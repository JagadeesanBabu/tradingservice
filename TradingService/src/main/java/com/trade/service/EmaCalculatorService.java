package com.trade.service;

import com.trade.pojo.TradeMovAvgConvDivgDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface EmaCalculatorService {
    Map<BigDecimal, BigDecimal> calculate(List<BigDecimal> closedPriceValues);

    List<BigDecimal> calculateMacd(Map<BigDecimal, BigDecimal> emaMap);

    List<TradeMovAvgConvDivgDto> calculateNineEmaMacd(List<BigDecimal> macd);
}
