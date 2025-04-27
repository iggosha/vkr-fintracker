package ru.golovkov.finparser.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ParsedEntitiesDto {

    private AccountDto account;
    private ClientDto client;
    private List<MoneyFlowDto> moneyFlows = new ArrayList<>();
}
