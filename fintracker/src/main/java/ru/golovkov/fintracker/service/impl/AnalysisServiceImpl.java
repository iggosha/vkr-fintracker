package ru.golovkov.fintracker.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.golovkov.fintracker.dto.InflowsAndOutflowsDto;
import ru.golovkov.fintracker.repository.MoneyFlowRepository;
import ru.golovkov.fintracker.service.AnalysisService;
import ru.golovkov.fintracker.strategy.ForecastStrategy;
import ru.golovkov.fintracker.strategy.ForecastStrategyFactory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AnalysisServiceImpl implements AnalysisService {

    private final MoneyFlowRepository moneyFlowRepository;

    @Override
    public Map<String, BigDecimal> getOutflowsByCategories(UUID clientId, LocalDate from, LocalDate to) {
        List<Object[]> outflowsByCategory = moneyFlowRepository.getOutflowsByCategory(clientId, from, to);
        return mapRowToMap(
                outflowsByCategory,
                row -> (String) row[0],
                row -> (BigDecimal) row[1]
        );
    }

    @Override
    public Map<YearMonth, InflowsAndOutflowsDto> getMonthlyInflowsAndOutflows(UUID clientId, LocalDate from, LocalDate to) {
        List<Object[]> rows = moneyFlowRepository.getMonthlyInflowsAndOutflows(clientId, from, to);
        return mapRowToMap(
                rows,
                row -> YearMonth.of((Integer) row[0], (Integer) row[1]),
                row -> new InflowsAndOutflowsDto((BigDecimal) row[2], ((BigDecimal) row[3]))
        );
    }

    @Override
    public InflowsAndOutflowsDto getTotalInflowAndOutflow(UUID clientId, LocalDate from, LocalDate to) {
        List<Object[]> row = moneyFlowRepository.getTotalInflowAndOutflow(clientId, from, to);
        return new InflowsAndOutflowsDto((BigDecimal) row.getFirst()[0], (BigDecimal) row.getFirst()[1]);
    }

    @Override
    public Map<YearMonth, BigDecimal> getMonthlyNetChangeForecast(UUID clientId, Integer monthAmount, String strategyType) {
        List<Object[]> rows = moneyFlowRepository.getMonthlyNetChange(clientId);
        TreeMap<YearMonth, BigDecimal> monthlyNetChangeMap = mapRowToMap(
                rows,
                row -> YearMonth.of((Integer) row[0], (Integer) row[1]),
                row -> (BigDecimal) row[2]
        );
        ForecastStrategy strategy = ForecastStrategyFactory.getStrategy(strategyType);
        Map<YearMonth, BigDecimal> forecastNetChangeMap =
                strategy.generateForecast(monthlyNetChangeMap, monthAmount);
        monthlyNetChangeMap.putAll(forecastNetChangeMap);
        return monthlyNetChangeMap;
    }

    private <K, V> TreeMap<K, V> mapRowToMap(List<Object[]> rows,
                                             Function<Object[], K> key,
                                             Function<Object[], V> value) {
        return rows.stream()
                .collect(Collectors.toMap(
                        key,
                        value,
                        (a, b) -> b,
                        TreeMap::new
                ));
    }
}
