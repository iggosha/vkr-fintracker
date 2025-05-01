package ru.golovkov.fintracker.strategy;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.YearMonth;
import java.util.Map;
import java.util.TreeMap;


public class AverageStrategy implements ForecastStrategy {

    @Override
    public Map<YearMonth, BigDecimal> generateForecast(TreeMap<YearMonth, BigDecimal> actualNetChangeMap, int forecastMonths) {
        if (actualNetChangeMap.isEmpty()) {
            return Map.of();
        }

        BigDecimal sum = actualNetChangeMap
                .values()
                .stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal average = sum.divide(BigDecimal.valueOf(actualNetChangeMap.size()), RoundingMode.HALF_UP);
        YearMonth lastMonth = actualNetChangeMap.lastKey();

        Map<YearMonth, BigDecimal> forecast = new TreeMap<>();
        for (int i = 1; i <= forecastMonths; i++) {
            forecast.put(lastMonth.plusMonths(i), average);
        }

        return forecast;
    }
}
