package ru.golovkov.fintracker.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.golovkov.fintracker.dto.ClientDto;
import ru.golovkov.fintracker.service.ClientService;

import java.util.List;
import java.util.UUID;

@Transactional
@Service
@RequiredArgsConstructor
@Slf4j
public class ClientServiceImpl implements ClientService {

    @Override
    public ClientDto create(ClientDto dto) {
        return null;
    }

    @Override
    public List<ClientDto> createAll(List<ClientDto> dtos) {
        return List.of();
    }

    @Override
    public ClientDto getById(UUID id) {
        return null;
    }

    @Override
    public List<ClientDto> getAll(Pageable pageable) {
        return List.of();
    }

    @Override
    public ClientDto updateById(UUID id, ClientDto dto) {
        return null;
    }

    @Override
    public void deleteById(UUID id) {

    }

    @Override
    public void deleteAllByIds(List<UUID> ids) {

    }

    @Override
    public void deleteAll() {

    }
}
