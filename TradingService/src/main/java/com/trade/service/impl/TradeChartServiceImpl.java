package com.trade.service.impl;

import com.trade.entity.TradingData;
import com.trade.jpa.TradeDataRepository;
import com.trade.mapper.TradeDataMapper;
import com.trade.mapper.TradeRelativeDataMapper;
import com.trade.pojo.TradeDataBaseDto;
import com.trade.pojo.TradeRelativeDataDto;
import com.trade.pojo.request.FilterRequest;
import com.trade.service.RelativeIndexCalculator;
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
    private final TradeRelativeDataMapper relativeDataMapper;
    private final RelativeIndexCalculator relativeIndexCalculator;

    @Override
    public List<TradeDataBaseDto> getDailyTradeClosedPriceByIndex(String index, FilterRequest filterRequest) {
        PageRequest pageRequest = PageRequest.of(filterRequest.getFrom(), filterRequest.getSize(), filterRequest.getSortOrder(), filterRequest.getSortValue());
        return mapper.toTradeDataDtoList(repository.findAllByIndex(index, pageRequest));
    }

    @Override
    public List<TradeRelativeDataDto> getDailyRelativeTradeDataByIndex(String index, FilterRequest filterRequest) {

        PageRequest pageRequest = PageRequest.of(filterRequest.getFrom(), filterRequest.getSize(), filterRequest.getSortOrder(), filterRequest.getSortValue());
        log.info("pageRequest : {}", pageRequest);
       List<TradeRelativeDataDto> initialTradeRelativeDataDto = relativeDataMapper.toTradeRelativeDataDtoList(repository.findAllByIndex(index, pageRequest));
       List<TradeRelativeDataDto> priceChangeTradeRelativeDataDtos = relativeIndexCalculator.calculatePriceChange(initialTradeRelativeDataDto);
       return relativeIndexCalculator.calculateRelativeIndexByFactor(priceChangeTradeRelativeDataDtos, filterRequest.getRelativeIndexFactor());
    }


}
