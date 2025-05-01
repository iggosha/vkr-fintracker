package ru.golovkov.fintracker.strategy;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.YearMonth;
import java.util.Map;
import java.util.TreeMap;

public class LinearRegressionStrategy implements ForecastStrategy {

    @Override
    public Map<YearMonth, BigDecimal> generateForecast(TreeMap<YearMonth, BigDecimal> actualNetChangeMap, int forecastMonths) {
        if (actualNetChangeMap.isEmpty()) {
            return Map.of();
        }

        int actualNetChangeMapSize = actualNetChangeMap.size();
        YearMonth lastMonth = actualNetChangeMap.lastKey();

        BigDecimal sumX = BigDecimal.ZERO;
        BigDecimal sumY = BigDecimal.ZERO;
        BigDecimal sumXY = BigDecimal.ZERO;
        BigDecimal sumX2 = BigDecimal.ZERO;

        int index = 1;
        for (BigDecimal y : actualNetChangeMap.values()) {
            BigDecimal x = BigDecimal.valueOf(index);
            sumX = sumX.add(x);
            sumY = sumY.add(y);
            sumXY = sumXY.add(x.multiply(y));
            sumX2 = sumX2.add(x.multiply(x));
            index++;
        }

        BigDecimal meanX = sumX.divide(BigDecimal.valueOf(actualNetChangeMapSize), RoundingMode.HALF_UP);
        BigDecimal meanY = sumY.divide(BigDecimal.valueOf(actualNetChangeMapSize), RoundingMode.HALF_UP);
        BigDecimal numerator = sumXY.subtract(sumX.multiply(meanY));
        BigDecimal denominator = sumX2.subtract(sumX.multiply(meanX));

        BigDecimal slope = numerator.divide(denominator, 10, RoundingMode.HALF_UP);
        BigDecimal intercept = meanY.subtract(slope.multiply(meanX));

        Map<YearMonth, BigDecimal> forecast = new TreeMap<>();
        for (int i = 1; i <= forecastMonths; i++) {
            BigDecimal x = BigDecimal.valueOf(actualNetChangeMapSize + (long) i);
            BigDecimal forecastValue = slope.multiply(x).add(intercept).setScale(2, RoundingMode.HALF_UP);
            YearMonth forecastMonth = lastMonth.plusMonths(i);
            forecast.put(forecastMonth, forecastValue);
        }

        return forecast;
    }
}
