package ru.golovkov.fintracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class InflowsAndOutflowsDto {

    private BigDecimal outflows;
    private BigDecimal inflows;
}
