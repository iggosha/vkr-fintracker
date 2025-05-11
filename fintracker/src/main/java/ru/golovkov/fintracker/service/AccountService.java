package ru.golovkov.fintracker.service;

import ru.golovkov.fintracker.dto.AccountDto;

import java.util.List;
import java.util.UUID;

public interface AccountService extends CrudService<String, AccountDto> {

    AccountDto create(UUID clientId);

    List<AccountDto> getAllByClientId(UUID clientId);

    void deleteAllByClientId(UUID clientId);
}
