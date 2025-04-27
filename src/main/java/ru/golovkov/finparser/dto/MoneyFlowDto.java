package ru.golovkov.finparser.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class MoneyFlowDto implements Serializable {

    private String id;
    private LocalDate date;
    private String description;
    private BigDecimal amount;
    private String category;
    private String additionalInfo;
}