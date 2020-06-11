package com.trade.service;

import com.trade.pojo.TradeRelativeDataDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
@RequiredArgsConstructor
@Slf4j
public class RelativeIndexCalculator {

    public List<TradeRelativeDataDto> calculatePriceChange(List<TradeRelativeDataDto> tradeRelativeDataDtos) {
        List<TradeRelativeDataDto> changedTradeDataDtos = new ArrayList<>();

        IntStream.range(0, tradeRelativeDataDtos.size())
                .forEach(index -> {
                    TradeRelativeDataDto dataDto = tradeRelativeDataDtos.get(index);
                    log.info("TradeRelativeDataDto : {}", dataDto);
                    if (index > 0) {
                        BigDecimal currentClosePrice = dataDto.getClosedPrice();
                        BigDecimal previousClosePrice = tradeRelativeDataDtos.get(index - 1).getClosedPrice();
                        dataDto.setPriceChange(subtract(previousClosePrice, currentClosePrice));
                        log.info("Setting price change {}", subtract(previousClosePrice, currentClosePrice));
                    }
                    changedTradeDataDtos.add(dataDto);
                    log.info("size of changedTradeDto {} ", changedTradeDataDtos.size());
                });

        return changedTradeDataDtos;
    }

    private BigDecimal subtract(BigDecimal previousClosePrice, BigDecimal currentClosePrice) {
        return Objects.isNull(previousClosePrice) ? null : currentClosePrice.subtract(previousClosePrice);
    }

    public List<TradeRelativeDataDto> calculateRelativeIndexByFactor(List<TradeRelativeDataDto> tradeDataDtos, Integer relativeIndexFactor) {

        List<TradeRelativeDataDto> changedTradeDataDtos = new ArrayList<>();

        //To fetch the last relative index factor number of elements
        AtomicInteger j = new AtomicInteger(0);

        //Get Initial Records until relative index factor
        IntStream.range(0, relativeIndexFactor)
                .forEach(index -> changedTradeDataDtos.add(tradeDataDtos.get(index)));

        //Calculate relative index by factor from pos : relationIndexFactor
        IntStream.range(relativeIndexFactor, tradeDataDtos.size())
                .forEach(index -> {
                    if ((index + 1) < tradeDataDtos.size() && (relativeIndexFactor + j.get()) < tradeDataDtos.size()) {
                        //Step 1: Take the elements from j until relative indexFactor to get the positive and negative avg
                        List<TradeRelativeDataDto> avgTradeDto = IntStream.range(j.incrementAndGet(), relativeIndexFactor + j.get())
                                .mapToObj(i -> tradeDataDtos.get(i))
                                .collect(Collectors.toList());

                        //Step 2: calculate positive & negative price change
                        List<BigDecimal> positvePriceChange = avgTradeDto.stream()
                                .filter(tradeDataDto -> !(tradeDataDto.getPriceChange().signum() == -1))
                                .map(TradeRelativeDataDto::getPriceChange)
                                .collect(Collectors.toList());

                        //Step3: calculate negative price change
                        List<BigDecimal> negativePriceChange = avgTradeDto.stream()
                                .filter(tradeDataDto -> (tradeDataDto.getPriceChange().signum() == -1))
                                .map(tradeDto -> tradeDto.getPriceChange().negate())
                                .collect(Collectors.toList());

                        //Step 3: calculate avgpositive & negative gain
                        BigDecimal avgPositiveGain = calculateAverage(positvePriceChange, relativeIndexFactor);
                        BigDecimal avgNegativeGain = calculateAverage(negativePriceChange, relativeIndexFactor);

                        //Step 4: set the calculated values
                        TradeRelativeDataDto tradeDataDto = tradeDataDtos.get(index + 1);
                        tradeDataDto.setAvgChangeGain(avgPositiveGain);
                        tradeDataDto.setAvgChangeLoss(avgNegativeGain);
                        BigDecimal relativeStrength = avgPositiveGain.divide(avgNegativeGain, RoundingMode.HALF_UP);
                        tradeDataDto.setRelativeStrength(relativeStrength);
                        tradeDataDto.setRelativeIndex(calculateRelativeIndex(relativeStrength));
                        changedTradeDataDtos.add(tradeDataDto);
                    }
                });

        return changedTradeDataDtos;

    }

    private BigDecimal calculateRelativeIndex(BigDecimal relativeStrength) {
        BigDecimal addValue = relativeStrength.add(BigDecimal.ONE);
        BigDecimal topValue = new BigDecimal(100).divide(addValue, RoundingMode.HALF_UP);
        return new BigDecimal(100).subtract(topValue);
    }

    private BigDecimal calculateAverage(List<BigDecimal> bigDecimals, int rsiSize) {
        BigDecimal sum = bigDecimals.stream()
                .map(Objects::requireNonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return sum.divide(new BigDecimal(rsiSize), RoundingMode.HALF_UP);
    }
}
