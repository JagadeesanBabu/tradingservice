package com.trade.service;

import com.trade.pojo.TradeDataBaseDto;
import com.trade.pojo.TradeMovAvgConvDivgDto;
import com.trade.pojo.TradeRelativeDataDto;
import com.trade.pojo.request.FilterRequest;

import java.util.List;

public interface TradeChartService {
    List<TradeDataBaseDto> getDailyTradeClosedPriceByIndex(String index, FilterRequest filterRequest);
    List<TradeRelativeDataDto> getDailyRelativeTradeDataByIndex(String index, FilterRequest filterRequest, Integer relativeIndexFactor);
    List<TradeMovAvgConvDivgDto> getDailyTradeDataByMacd(String index, FilterRequest filterRequest);
}
