package ru.golovkov.fintracker.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.golovkov.fintracker.dto.MoneyFlowDto;
import ru.golovkov.fintracker.dto.parsed.ParsedMoneyFlowDto;
import ru.golovkov.fintracker.model.MoneyFlow;

import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = SPRING, nullValuePropertyMappingStrategy = IGNORE)
public interface MoneyFlowMapper {

    @Mapping(target = "category", ignore = true)
    @Mapping(target = "account", ignore = true)
    MoneyFlow mapToEntityFromParsedDto(ParsedMoneyFlowDto parsedDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "account", ignore = true)
    MoneyFlow mapToEntityFromDto(MoneyFlowDto dto);

    @Mapping(target = "categoryId", source = "category.id")
    @Mapping(target = "categoryName", source = "category.name")
    MoneyFlowDto mapToDtoFromEntity(MoneyFlow entity);

    List<MoneyFlowDto> mapToDtosFromEntities(List<MoneyFlow> entities);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "account", ignore = true)
    void updateEntityFromDto(@MappingTarget MoneyFlow entity, MoneyFlowDto dto);

}
