package ru.golovkov.fintracker.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.golovkov.fintracker.dto.ClientDto;
import ru.golovkov.fintracker.model.Client;

import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = SPRING, nullValuePropertyMappingStrategy = IGNORE)
public interface ClientMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "accounts", ignore = true)
    Client mapToEntityFromDto(ClientDto dto);

    ClientDto mapToDtoFromEntity(Client entity);

    List<ClientDto> mapToDtosFromEntities(List<Client> entities);
}
