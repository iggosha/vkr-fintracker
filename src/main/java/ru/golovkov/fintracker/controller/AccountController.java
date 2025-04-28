package ru.golovkov.fintracker.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.golovkov.fintracker.dto.AccountDto;
import ru.golovkov.fintracker.service.AccountService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class AccountController {

    private final AccountService service;

    @PostMapping("/clients/{clientId}/accounts")
    public AccountDto create(@PathVariable UUID clientId) {
        return service.create(clientId);
    }

    @GetMapping("/accounts")
    public List<AccountDto> getAll() {
        return service.getAll();
    }

    @GetMapping("/clients/{clientId}/accounts")
    public List<AccountDto> getAllByClientId(@PathVariable UUID clientId) {
        return service.getAllByClientId(clientId);
    }

    @GetMapping("/accounts/{id}")
    public AccountDto getById(@PathVariable String id) {
        return service.getById(id);
    }

    @DeleteMapping("/accounts/{id}")
    public void deleteById(@PathVariable String id) {
        service.deleteById(id);
    }

    @DeleteMapping("/clients/{clientId}/accounts")
    public void deleteAllByClientId(@PathVariable UUID clientId) {
        service.deleteAllByClientId(clientId);
    }
}
