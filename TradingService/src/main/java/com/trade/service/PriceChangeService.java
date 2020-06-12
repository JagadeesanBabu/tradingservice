package com.trade.service;

import com.trade.pojo.PriceChangeDataDto;
import com.trade.pojo.TradeDataBaseDto;

import java.util.List;

public interface PriceChangeService {
    List<PriceChangeDataDto> calculate(List<TradeDataBaseDto> tradeDataBaseDtos);
}
