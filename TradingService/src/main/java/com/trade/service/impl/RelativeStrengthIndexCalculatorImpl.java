package com.trade.service.impl;

import com.trade.pojo.AverageDataDto;
import com.trade.pojo.TradeRelativeDataDto;
import com.trade.service.RelativeStrengthIndexCalculator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
@Slf4j
public class RelativeStrengthIndexCalculatorImpl implements RelativeStrengthIndexCalculator {

    @Override
    public List<TradeRelativeDataDto> calculate(List<AverageDataDto> averageDataDtos) {
        var relativeDataDtos = new ArrayList<TradeRelativeDataDto>();
        IntStream.range(0, averageDataDtos.size())
                .forEach(index -> {

                    AverageDataDto averageDataDto = averageDataDtos.get(index);
                    log.info("Calculate relative Strength & index for averageDto {}",averageDataDto);
                    BigDecimal relativeStrength = averageDataDto.getAvgChangeGain().divide(averageDataDto.getAvgChangeLoss(), RoundingMode.HALF_UP);
                    BigDecimal relativeIndex = calculateRelativeIndex(relativeStrength);
                    TradeRelativeDataDto relativeDataDto = TradeRelativeDataDto.builder().relativeIndex(relativeIndex).relativeStrength(relativeStrength).build();
                    relativeDataDtos.add(relativeDataDto);
                });
        return relativeDataDtos;
    }

    private BigDecimal calculateRelativeIndex(BigDecimal relativeStrength) {
        BigDecimal addValue = relativeStrength.add(BigDecimal.ONE);
        BigDecimal topValue = new BigDecimal(100).divide(addValue, RoundingMode.HALF_UP);
        return new BigDecimal(100).subtract(topValue);
    }

}
