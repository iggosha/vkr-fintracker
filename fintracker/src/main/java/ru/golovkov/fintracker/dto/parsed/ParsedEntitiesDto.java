package ru.golovkov.fintracker.dto.parsed;

import lombok.Data;

import java.util.List;

@Data
public class ParsedEntitiesDto {

    private ParsedAccountDto account;
    private ParsedClientDto client;
    private List<ParsedMoneyFlowDto> moneyFlows;
}
