package ru.golovkov.fintracker.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.golovkov.fintracker.dto.InflowsAndOutflowsDto;
import ru.golovkov.fintracker.service.AnalysisService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class AnalysisController {

    private final AnalysisService service;

    @GetMapping("/clients/{clientId}/outflows")
    public Map<String, BigDecimal> getOutflowsByCategories(
            @PathVariable UUID clientId,
            @RequestParam(required = false) LocalDate from,
            @RequestParam(required = false) LocalDate to) {
        return service.getOutflowsByCategories(clientId, from, to);
    }

    @GetMapping("/clients/{clientId}/monthly-flows")
    public Map<YearMonth, InflowsAndOutflowsDto> getMonthlyInflowsAndOutflows(
            @PathVariable UUID clientId,
            @RequestParam(required = false) LocalDate from,
            @RequestParam(required = false) LocalDate to) {
        return service.getMonthlyInflowsAndOutflows(clientId, from, to);
    }

    @GetMapping("/clients/{clientId}/total-flows")
    public InflowsAndOutflowsDto getTotalInflowAndOutflow(
            @PathVariable UUID clientId,
            @RequestParam(required = false) LocalDate from,
            @RequestParam(required = false) LocalDate to) {
        return service.getTotalInflowAndOutflow(clientId, from, to);
    }

    @GetMapping("/clients/{clientId}/forecast")
    public Map<YearMonth, BigDecimal> getMonthlyNetChangeForecast(
            @PathVariable UUID clientId,
            @RequestParam Integer monthAmount,
            @RequestParam(required = false, defaultValue = "AVG") String strategyType) {
        return service.getMonthlyNetChangeForecast(clientId, monthAmount, strategyType);
    }
}
