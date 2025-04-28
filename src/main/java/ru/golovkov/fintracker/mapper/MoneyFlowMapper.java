package ru.golovkov.fintracker.mapper;

import org.mapstruct.Mapper;
import ru.golovkov.fintracker.dto.MoneyFlowDto;
import ru.golovkov.fintracker.dto.parsed.ParsedMoneyFlowDto;
import ru.golovkov.fintracker.model.MoneyFlow;

import java.util.List;

@Mapper
public interface MoneyFlowMapper {

    MoneyFlow mapToEntityFromParsed(ParsedMoneyFlowDto parsedMoneyFlowDto);

    MoneyFlowDto mapToDtoFromEntity(MoneyFlow moneyFlow);

    List<MoneyFlowDto> mapToDtosFromEntities(List<MoneyFlow> moneyFlows);
}
