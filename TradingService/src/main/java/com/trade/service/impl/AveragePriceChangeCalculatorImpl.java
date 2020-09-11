package com.trade.service.impl;

import com.trade.pojo.AverageDataDto;
import com.trade.pojo.PriceChangeDataDto;
import com.trade.service.AveragePriceChangeCalculator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.trade.service.impl.ArithmeticHelper.calculateAverage;

@Service
@Slf4j
@RequiredArgsConstructor
public class AveragePriceChangeCalculatorImpl implements AveragePriceChangeCalculator {

    @Override
    public List<AverageDataDto> calculate(List<PriceChangeDataDto> priceChangeDataDtos, Integer periodLength) {
        List<AverageDataDto> averageDataDtos = new ArrayList<>();

        //To hold the pervious average value
        AtomicReference<AverageDataDto> previousAvgData = new AtomicReference<>();
        AtomicInteger index = new AtomicInteger();
        index.set(periodLength + 1);
        //Calculate averageGain and average loss for a period length
        IntStream.range(index.get(), priceChangeDataDtos.size())
                .forEach(indexRange -> {

                    if (indexRange == periodLength + 1) {
                        log.info("Calculating initial average until period length elements");
                        AverageDataDto initialAvgData = calculateInitialReferenceAverage(priceChangeDataDtos, periodLength);
                        if (Objects.nonNull(initialAvgData)) {
                            averageDataDtos.add(initialAvgData);
                        }
                        previousAvgData.set(initialAvgData);
                    } else if (Objects.nonNull(previousAvgData.get())) {
                        //Else normal flow calculation
                        log.info("previousAvgGainvalue*periodLength + (PositivePriceChange) / (periodLength + 1) ");
                        BigDecimal currentPriceChangeValue = priceChangeDataDtos.get(indexRange).getPriceChangeValue();
                        BigDecimal positivePriceChangValue = !(currentPriceChangeValue.signum() == -1) ? currentPriceChangeValue : BigDecimal.ZERO;
                        BigDecimal negativePriceChangeValue = (currentPriceChangeValue.signum() == -1) ? currentPriceChangeValue.negate() : BigDecimal.ZERO;
                        BigDecimal calculatedPreviousAvgChangeGain = previousAvgData.get().getAvgChangeGain().multiply(new BigDecimal(periodLength)).add(positivePriceChangValue);
                        BigDecimal calculatedPreviousAvgChangeLoss = previousAvgData.get().getAvgChangeLoss().multiply(new BigDecimal(periodLength)).add(negativePriceChangeValue);
                        BigDecimal averageGain = calculatedPreviousAvgChangeGain.divide(new BigDecimal(periodLength + 1), RoundingMode.HALF_UP);
                        BigDecimal averageLoss = calculatedPreviousAvgChangeLoss.divide(new BigDecimal(periodLength + 1), RoundingMode.HALF_UP);
                        AverageDataDto currentAverageDataDto = AverageDataDto.builder()
                                .avgChangeLoss(averageLoss)
                                .avgChangeGain(averageGain)
                                .tradeDate(priceChangeDataDtos.get(indexRange).getTradeDate())
                                .build();
                        averageDataDtos.add(currentAverageDataDto);
                        previousAvgData.set(currentAverageDataDto);

                    }
                });

        return averageDataDtos;

    }

    private AverageDataDto calculateInitialReferenceAverage(List<PriceChangeDataDto> priceChangeDataDtos, int periodLength) {

        //Step1 : get price change until period length
        List<PriceChangeDataDto> priceChangeUntilPeriodLength = IntStream.range(1, periodLength + 1)
                .mapToObj(i -> priceChangeDataDtos.get(i))
                .collect(Collectors.toList());

        //Step 2: calculate positive & negative price change
        List<BigDecimal> positvePriceChange = priceChangeUntilPeriodLength.stream()
                .filter(priceChangeDataDto -> !(priceChangeDataDto.getPriceChangeValue().signum() == -1))
                .map(PriceChangeDataDto::getPriceChangeValue)
                .collect(Collectors.toList());

        //Step3: calculate negative price change
        List<BigDecimal> negativePriceChange = priceChangeUntilPeriodLength.stream()
                .filter(priceChangeDataDto -> (priceChangeDataDto.getPriceChangeValue().signum() == -1))
                .map(priceChangeDataDto -> priceChangeDataDto.getPriceChangeValue().negate())
                .collect(Collectors.toList());

        //Step 3: calculate avgpositive & negative gain
        BigDecimal avgPositiveGain = calculateAverage(positvePriceChange, periodLength);
        BigDecimal avgNegativeGain = calculateAverage(negativePriceChange, periodLength);

        return AverageDataDto.builder()
                .avgChangeGain(avgPositiveGain)
                .avgChangeLoss(avgNegativeGain)
                .tradeDate(priceChangeDataDtos.get(periodLength + 1).getTradeDate())
                .build();

    }


}
