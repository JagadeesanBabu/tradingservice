package com.trade.service.impl;

import com.trade.jpa.TradeDataRepository;
import com.trade.mapper.TradeDataMapper;
import com.trade.pojo.AverageDataDto;
import com.trade.pojo.PriceChangeDataDto;
import com.trade.pojo.TradeDataBaseDto;
import com.trade.pojo.TradeRelativeDataDto;
import com.trade.pojo.request.FilterRequest;
import com.trade.service.AveragePriceChangeCalculator;
import com.trade.service.PriceChangeService;
import com.trade.service.RelativeStrengthIndexCalculator;
import com.trade.service.TradeChartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TradeChartServiceImpl implements TradeChartService {

    private final TradeDataRepository repository;
    private final TradeDataMapper mapper;
    private final RelativeStrengthIndexCalculator relativeIndexCalculator;
    private final AveragePriceChangeCalculator averagePriceChangeCalculator;
    private final PriceChangeService priceChangeService;

    @Override
    public List<TradeDataBaseDto> getDailyTradeClosedPriceByIndex(String index, FilterRequest filterRequest) {
        return mapper.toTradeDataDtoList(repository.findAllByIndex(index, getPageRequest(filterRequest)));
    }

    @Override
    public List<TradeRelativeDataDto> getDailyRelativeTradeDataByIndex(String index, FilterRequest filterRequest, Integer periodLength) {
        List<TradeDataBaseDto> initialTradeData = mapper.toTradeDataDtoList(repository.findAllByIndex(index, getPageRequest(filterRequest)));
        //Step 1: price change calculator
        List<PriceChangeDataDto> priceChangeDataDtos = priceChangeService.calculate(initialTradeData);
        //Step 2: average gain loss on price change calculator
        List<AverageDataDto> averageDataDtos = averagePriceChangeCalculator.calculate(priceChangeDataDtos, periodLength);
        //Step 3: relative index calculator
        return relativeIndexCalculator.calculate(averageDataDtos);
    }

    private PageRequest getPageRequest(FilterRequest filterRequest) {
        return PageRequest.of(filterRequest.getFrom(), filterRequest.getSize(), filterRequest.getSortOrder(), filterRequest.getSortValue());
    }
}
