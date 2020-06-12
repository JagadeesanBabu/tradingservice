package com.trade.service;

import com.trade.pojo.AverageDataDto;
import com.trade.pojo.TradeRelativeDataDto;

import java.util.List;

public interface RelativeStrengthIndexCalculator {
    List<TradeRelativeDataDto> calculate(List<AverageDataDto> averageDataDtos);
}
