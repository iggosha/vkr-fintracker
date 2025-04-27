package ru.golovkov.fintracker.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO for {@link ru.golovkov.fintracker.model.MoneyFlow}
 */
@Data
public class MoneyFlowDto implements Serializable {

    private String id;
    private LocalDate date;
    private String description;
    private BigDecimal amount;
    private String additionalInfo;
}