package ru.golovkov.fintracker.service;

import ru.golovkov.fintracker.dto.InflowsAndOutflowsDto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Map;
import java.util.UUID;

public interface AnalysisService {

    Map<String, BigDecimal> getOutflowsByCategories(UUID clientId, LocalDate from, LocalDate to);

    Map<YearMonth, InflowsAndOutflowsDto> getMonthlyInflowsAndOutflows(UUID clientId, LocalDate from, LocalDate to);

    InflowsAndOutflowsDto getTotalInflowAndOutflow(UUID clientId, LocalDate from, LocalDate to);

    Map<YearMonth, BigDecimal> getMonthlyNetChangeForecast(UUID clientId, Integer monthAmount, String strategyType);
}
