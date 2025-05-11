package ru.golovkov.fintracker.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.golovkov.fintracker.dto.AccountDto;
import ru.golovkov.fintracker.model.Account;

import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = SPRING, nullValuePropertyMappingStrategy = IGNORE)
public interface AccountMapper {

    @Mapping(target = "clientId", source = "client.id")
    AccountDto mapToDtoFromEntity(Account entity);

    List<AccountDto> mapToDtosFromEntities(List<Account> entities);
}
