package com.trade.service.impl;

import com.trade.jpa.TradeDataRepository;
import com.trade.mapper.TradeDataMapper;
import com.trade.pojo.*;
import com.trade.pojo.request.FilterRequest;
import com.trade.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class TradeChartServiceImpl implements TradeChartService {

    private final TradeDataRepository repository;
    private final TradeDataMapper mapper;
    private final RelativeStrengthIndexCalculator relativeIndexCalculator;
    private final AveragePriceChangeCalculator averagePriceChangeCalculator;
    private final PriceChangeService priceChangeService;
    private final EmaCalculatorService emaCalculatorService;

    @Override
    public List<TradeDataBaseDto> getDailyTradeClosedPriceByIndex(String index, FilterRequest filterRequest) {
        return (Objects.nonNull(filterRequest.getFromDate()) && Objects.nonNull(filterRequest.getEndDate())) ?
                mapper.toTradeDataDtoList(repository.findAllByTradeDateBetweenAndIndex(filterRequest.getFromDate(), filterRequest.getEndDate(), index, getPageRequest(filterRequest))) :
                mapper.toTradeDataDtoList(repository.findAllByIndex(index, getPageRequest(filterRequest)));
    }

    @Override
    public List<TradeRelativeDataDto> getDailyRelativeTradeDataByIndex(String index, FilterRequest filterRequest, Integer periodLength) {
        List<TradeDataBaseDto> initialTradeData = this.getDailyTradeClosedPriceByIndex(index, filterRequest);
        //Step 1: price change calculator
        List<PriceChangeDataDto> priceChangeDataDtos = priceChangeService.calculate(initialTradeData);
        //Step 2: average gain loss on price change calculator
        List<AverageDataDto> averageDataDtos = averagePriceChangeCalculator.calculate(priceChangeDataDtos, periodLength);
        //Step 3: relative index calculator
        return relativeIndexCalculator.calculate(averageDataDtos);
    }

    @Override
    public List<TradeMovAvgConvDivgDto> getDailyTradeDataByMacd(String index, FilterRequest filterRequest) {
        List<TradeDataBaseDto> initialTradeData = this.getDailyTradeClosedPriceByIndex(index, filterRequest);

        //Step 1: Calculate 12 EMA & 26 EMA
        // Key 12 EMA & 26 EMA
        List<BigDecimal> closedPriceValues = initialTradeData.stream().map(TradeDataBaseDto::getClosedPrice).collect(Collectors.toList());
        Map<BigDecimal, BigDecimal> emaMap = emaCalculatorService.calculate(closedPriceValues);
        //Step 2: Calculate MACD (Difference between 12 EMA - 26 EMA)
        List<BigDecimal> macds = emaCalculatorService.calculateMacd(emaMap);
        //Step 3: Calculate 9MACD
        List<TradeMovAvgConvDivgDto> nineMacdAverage = emaCalculatorService.calculateNineEmaMacd(macds);

        return nineMacdAverage;
    }

    private PageRequest getPageRequest(FilterRequest filterRequest) {
        return PageRequest.of(filterRequest.getFrom(), filterRequest.getSize(), filterRequest.getSortOrder(), filterRequest.getSortValue());
    }
}
