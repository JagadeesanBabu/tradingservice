package com.trade.service.impl;

import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Objects;

@UtilityClass
public class ArithmeticHelper {

    public static BigDecimal calculateAverage(List<BigDecimal> bigDecimals, int periodLength) {
        BigDecimal sum = bigDecimals.stream()
                .map(Objects::requireNonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return sum.divide(new BigDecimal(periodLength), RoundingMode.HALF_UP);

    }


}
