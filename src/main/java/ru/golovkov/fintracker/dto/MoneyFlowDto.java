package ru.golovkov.fintracker.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class MoneyFlowDto {

    private String id;
    private LocalDate date;
    private String description;
    private BigDecimal amount;
    private String additionalInfo;
}