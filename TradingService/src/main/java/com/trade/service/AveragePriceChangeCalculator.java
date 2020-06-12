package com.trade.service;

import com.trade.pojo.AverageDataDto;
import com.trade.pojo.PriceChangeDataDto;

import java.util.List;

public interface AveragePriceChangeCalculator {
    List<AverageDataDto> calculate(List<PriceChangeDataDto> priceChangeDataDtos, Integer periodLength);
}
