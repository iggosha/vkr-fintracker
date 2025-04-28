package ru.golovkov.fintracker.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.golovkov.fintracker.dto.AccountDto;
import ru.golovkov.fintracker.exception.ResourceNotFoundException;
import ru.golovkov.fintracker.mapper.AccountMapper;
import ru.golovkov.fintracker.model.Account;
import ru.golovkov.fintracker.model.Client;
import ru.golovkov.fintracker.repository.AccountRepository;
import ru.golovkov.fintracker.repository.ClientRepository;
import ru.golovkov.fintracker.service.AccountService;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Transactional
@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {

    private final ClientRepository clientRepository;

    private final AccountRepository repository;
    private final AccountMapper mapper;
    private static final String EXC_MESSAGE_PATTERN = "Account with id %s was not found";

    @Override
    public AccountDto create(AccountDto dto) {
        return null;
    }

    @Override
    public AccountDto create(UUID clientId) {
        Client client = clientRepository
                .findById(clientId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Client with id %s was not found".formatted(clientId))
                );
        Account account = createVirtual(client);
        return mapper.mapToDtoFromEntity(repository.save(account));
    }

    @Override
    public List<AccountDto> getAllByClientId(UUID clientId) {
        return mapper.mapToDtosFromEntities(repository.findAllByClientId(clientId));
    }

    @Override
    public void deleteAllByClientId(UUID clientId) {
        repository.deleteByClientId(clientId);
    }

    @Override
    public AccountDto getById(String id) {
        return mapper.mapToDtoFromEntity(findByIdOrThrow(id));
    }

    @Override
    public List<AccountDto> getAll() {
        return mapper.mapToDtosFromEntities(repository.findAll());
    }

    @Override
    public AccountDto updateById(String id, AccountDto dto) {
        return null;
    }

    @Override
    public void deleteById(String id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            throw new ResourceNotFoundException(EXC_MESSAGE_PATTERN.formatted(id));
        }
    }

    @Override
    public void deleteAllByIds(List<String> ids) {
        repository.deleteAllById(ids);
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }

    private Account findByIdOrThrow(String id) {
        return repository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(EXC_MESSAGE_PATTERN.formatted(id))
                );
    }

    private Account createVirtual(Client client) {
        Account account = new Account();
        account.setId(UUID.randomUUID().toString());
        account.setType("Виртуальный счёт");
        account.setCreationDate(LocalDate.now());
        account.setClient(client);
        return account;
    }
}
