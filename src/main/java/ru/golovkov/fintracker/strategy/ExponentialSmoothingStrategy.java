package ru.golovkov.fintracker.strategy;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.YearMonth;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class ExponentialSmoothingStrategy implements ForecastStrategy {

    // Больше альфа - больше веса последним. Например, при 0.3 последние 3 месяца будут давать более половины влияния
    private static final BigDecimal ALPHA = BigDecimal.valueOf(0.4);
    // Больше бета - меньше плавность изменения тренда
    private static final BigDecimal BETA = BigDecimal.valueOf(0.2);

    @Override
    public Map<YearMonth, BigDecimal> generateForecast(TreeMap<YearMonth, BigDecimal> actualNetChangeMap, int forecastMonths) {
        if (actualNetChangeMap.size() < 2) return Map.of();

        Iterator<BigDecimal> it = actualNetChangeMap.values().iterator();
        BigDecimal level = it.next();
        BigDecimal trend = it.next().subtract(level);

        while (it.hasNext()) {
            BigDecimal y = it.next();
            BigDecimal prevLevel = level;
            level = ALPHA.multiply(y)
                    .add(BigDecimal.ONE.subtract(ALPHA)
                            .multiply(prevLevel.add(trend)));
            trend = BETA.multiply(level.subtract(prevLevel))
                    .add(BigDecimal.ONE.subtract(BETA).multiply(trend));
        }

        YearMonth lastMonth = actualNetChangeMap.lastKey();
        Map<YearMonth, BigDecimal> forecastMap = new TreeMap<>();
        for (int i = 1; i <= forecastMonths; i++) {
            BigDecimal forecastValue = level
                    .add(trend.multiply(BigDecimal.valueOf(i)))
                    .setScale(2, RoundingMode.HALF_UP);
            forecastMap.put(lastMonth.plusMonths(i), forecastValue);
        }

        return forecastMap;
    }
}
