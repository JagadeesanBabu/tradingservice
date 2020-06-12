package com.trade.service.impl;

import com.trade.mapper.TradePriceChangeDataMapper;
import com.trade.pojo.PriceChangeDataDto;
import com.trade.pojo.TradeDataBaseDto;
import com.trade.service.PriceChangeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
@Slf4j
public class PriceChangeServiceImpl implements PriceChangeService {

    private final TradePriceChangeDataMapper priceChangeDataMapper;

    @Override
    public List<PriceChangeDataDto> calculate(List<TradeDataBaseDto> tradeDataBaseDtos) {
        var priceChangeDataDtos = new ArrayList<PriceChangeDataDto>();

        IntStream.range(0, tradeDataBaseDtos.size())
                .forEach(index -> {
                    TradeDataBaseDto dataDto = tradeDataBaseDtos.get(index);
                    PriceChangeDataDto priceChangeDataDto = priceChangeDataMapper.toTradePriceChangeDataDto(dataDto);
                    log.info("TradeBaseDto : {}", dataDto);
                    if (index > 0) {
                        BigDecimal currentClosePrice = dataDto.getClosedPrice();
                        BigDecimal previousClosePrice = tradeDataBaseDtos.get(index - 1).getClosedPrice();
                        priceChangeDataDto.setPriceChangeValue(subtract(previousClosePrice, currentClosePrice));
                        log.info("Setting price change {}", subtract(previousClosePrice, currentClosePrice));
                    }
                    priceChangeDataDtos.add(priceChangeDataDto);
                    log.info("size of changedTradeDto {} ", priceChangeDataDtos.size());
                });

        return priceChangeDataDtos;
    }

    private BigDecimal subtract(BigDecimal previousClosePrice, BigDecimal currentClosePrice) {
        return Objects.isNull(previousClosePrice) ? null : currentClosePrice.subtract(previousClosePrice);
    }
}
