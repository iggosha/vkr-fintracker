package ru.golovkov.fintracker.strategy;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.YearMonth;
import java.util.Map;
import java.util.TreeMap;

public class ExponentialSmoothingStrategy implements ForecastStrategy {

    private static final BigDecimal ALPHA = BigDecimal.valueOf(0.5);
    private static final BigDecimal BETA = BigDecimal.valueOf(0.3);

    @Override
    public Map<YearMonth, BigDecimal> generateForecast(TreeMap<YearMonth, BigDecimal> actualNetChangeMap, int forecastMonths) {
        if (actualNetChangeMap.isEmpty()) return Map.of();
        YearMonth lastMonth = actualNetChangeMap.lastKey();
        BigDecimal level = actualNetChangeMap.firstEntry().getValue();
        BigDecimal trend = BigDecimal.ZERO;

        for (BigDecimal actual : actualNetChangeMap.values()) {
            BigDecimal prevLevel = level;
            level = ALPHA.multiply(actual).add(BigDecimal.ONE.subtract(ALPHA).multiply(prevLevel));
            trend = BETA.multiply(level.subtract(prevLevel)).add(BigDecimal.ONE.subtract(BETA).multiply(trend));
        }

        Map<YearMonth, BigDecimal> forecastMap = new TreeMap<>();
        for (int i = 1; i <= forecastMonths; i++) {
            YearMonth forecastMonth = lastMonth.plusMonths(i);
            BigDecimal forecastValue = level.add(trend.multiply(BigDecimal.valueOf(i))).setScale(2, RoundingMode.HALF_UP);
            forecastMap.put(forecastMonth, forecastValue);
        }

        return forecastMap;
    }
}
