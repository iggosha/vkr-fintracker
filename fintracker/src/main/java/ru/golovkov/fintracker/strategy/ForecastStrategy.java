package ru.golovkov.fintracker.strategy;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.Map;
import java.util.TreeMap;

public interface ForecastStrategy {

    Map<YearMonth, BigDecimal> generateForecast(TreeMap<YearMonth, BigDecimal> actualNetChangeMap, int forecastMonths);
}