package com.trade.service.impl;

import com.trade.pojo.TradeMovAvgConvDivgDto;
import com.trade.service.EmaCalculatorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmaCalculatorServiceImpl implements EmaCalculatorService {
    @Override
    public Map<BigDecimal, BigDecimal> calculate(List<BigDecimal> closedPriceValues) {

        Map<BigDecimal, BigDecimal> emaMap = new LinkedHashMap<>();
        AtomicReference<BigDecimal> previousEma12 = new AtomicReference<>();
        AtomicReference<BigDecimal> previousEma26 = new AtomicReference<>();

        IntStream.range(11, closedPriceValues.size())
                .forEach(
                        index -> {
                            //Calculated average of first 12 closed price
                            int ema12Length = 11;
                            int ema26Length = 25;
                            BigDecimal mapvalue = BigDecimal.ZERO;
                            int precision = 4;
                            if (index == ema12Length) {
                                previousEma12.set(calculateInitalEma(closedPriceValues, ema12Length).setScale(precision, RoundingMode.HALF_UP));
                                emaMap.put(previousEma12.get(), mapvalue);
                            } else if (index == ema26Length) {
                                previousEma26.set(calculateInitalEma(closedPriceValues, ema26Length).setScale(precision, RoundingMode.HALF_UP));
                                emaMap.put(previousEma12.get(), previousEma26.get());
                            } else {
                                BigDecimal numerator12 = closedPriceValues.get(index).subtract(previousEma12.get());
                                previousEma12.set(numerator12.multiply(new BigDecimal(0.1538)).add(previousEma12.get()).setScale(precision, RoundingMode.HALF_UP));

                                if (index > 25) {
                                    BigDecimal numerator26 = closedPriceValues.get(index).subtract(previousEma26.get());
                                    previousEma26.set(numerator26.multiply(new BigDecimal(0.0741)).add(previousEma26.get()).setScale(precision, RoundingMode.HALF_UP));
                                    mapvalue = previousEma26.get();
                                }
                                emaMap.put(previousEma12.get(), mapvalue);
                            }
                        }
                );
        return emaMap;
    }

    private BigDecimal calculateInitalEma(List<BigDecimal> closedPriceValues, int emaLength) {
        List<BigDecimal> closedPriceUntilPeriodLength = IntStream.range(0, emaLength)
                .mapToObj(i -> closedPriceValues.get(i))
                .collect(Collectors.toList());
        return ArithmeticHelper.calculateAverage(closedPriceUntilPeriodLength, emaLength);

    }

    @Override
    public List<BigDecimal> calculateMacd(Map<BigDecimal, BigDecimal> emaMap) {
        var macdValues = new ArrayList<BigDecimal>();
        emaMap.forEach((ema12, ema26) ->
        {
            if (BigDecimal.ZERO.compareTo(ema26) == -1) {
                macdValues.add(ema12.subtract(ema26).setScale(4, RoundingMode.HALF_UP));
            }
        });
        return macdValues;
    }

    @Override
    public List<TradeMovAvgConvDivgDto> calculateNineEmaMacd(List<BigDecimal> macd) {
        var macdValues = new ArrayList<TradeMovAvgConvDivgDto>();
        AtomicReference<BigDecimal> previousMacd9Avg = new AtomicReference<>();
        previousMacd9Avg.set(BigDecimal.ZERO);
        IntStream.range(0, macd.size()).forEach(
                index -> {
                    if (index == 8) {
                        previousMacd9Avg.set(calculateInitial9MacdAvg(macd, 8));
                    } else if (index > 8) {
                        BigDecimal numerator9Macd = macd.get(index).subtract(previousMacd9Avg.get());
                        previousMacd9Avg.set(numerator9Macd.multiply(new BigDecimal(0.2)).add(previousMacd9Avg.get()).setScale(4, RoundingMode.HALF_UP));
                    }
                    macdValues.add(TradeMovAvgConvDivgDto.builder().macd(macd.get(index)).nineMacd(previousMacd9Avg.get()).build());
                });

        return macdValues;
    }




    private BigDecimal calculateInitial9MacdAvg(List<BigDecimal> macd, int macd9PeriodLength) {
        List<BigDecimal> closedPriceUntilPeriodLength = IntStream.range(0, macd9PeriodLength)
                .mapToObj(i -> macd.get(i))
                .collect(Collectors.toList());
        return ArithmeticHelper.calculateAverage(closedPriceUntilPeriodLength, macd9PeriodLength);
    }

}

