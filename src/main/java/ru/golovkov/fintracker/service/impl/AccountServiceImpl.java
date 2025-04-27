package ru.golovkov.fintracker.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.golovkov.fintracker.dto.AccountDto;
import ru.golovkov.fintracker.service.AccountService;

import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {

    @Override
    public AccountDto create(AccountDto dto) {
        return null;
    }

    @Override
    public List<AccountDto> createAll(List<AccountDto> dtos) {
        return List.of();
    }

    @Override
    public AccountDto getById(String id) {
        return null;
    }

    @Override
    public List<AccountDto> getAll(Pageable pageable) {
        return List.of();
    }

    @Override
    public AccountDto updateById(String id, AccountDto dto) {
        return null;
    }

    @Override
    public void deleteById(String id) {

    }

    @Override
    public void deleteAllByIds(List<String> ids) {

    }

    @Override
    public void deleteAll() {

    }
}
