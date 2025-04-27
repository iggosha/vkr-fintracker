package ru.golovkov.fintracker.dto.parsed;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ParsedMoneyFlowDto {

    private String id;
    private LocalDate date;
    private String description;
    private BigDecimal amount;
    private String category;
    private String additionalInfo;
}
