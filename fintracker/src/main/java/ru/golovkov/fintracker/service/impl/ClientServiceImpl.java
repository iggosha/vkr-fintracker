package ru.golovkov.fintracker.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.golovkov.fintracker.dto.ClientDto;
import ru.golovkov.fintracker.exception.ResourceNotFoundException;
import ru.golovkov.fintracker.mapper.ClientMapper;
import ru.golovkov.fintracker.model.Client;
import ru.golovkov.fintracker.repository.ClientRepository;
import ru.golovkov.fintracker.service.ClientService;

import java.util.List;
import java.util.UUID;

@Transactional
@Service
@RequiredArgsConstructor
@Slf4j
public class ClientServiceImpl implements ClientService {

    private final ClientRepository repository;
    private final ClientMapper mapper;
    private static final String EXC_MESSAGE_PATTERN = "Client with id %s was not found";

    @Override
    public ClientDto create(ClientDto clientDto) {
        Client client = mapper.mapToEntityFromDto(clientDto);
        return mapper.mapToDtoFromEntity(repository.save(client));
    }

    @Override
    public ClientDto getById(UUID id) {
        return mapper.mapToDtoFromEntity(findByIdOrThrow(id));
    }

    @Override
    public List<ClientDto> getAll() {
        return mapper.mapToDtosFromEntities(repository.findAll());
    }

    @Override
    public ClientDto updateById(UUID id, ClientDto dto) {
        return null;
    }

    @Override
    public void deleteById(UUID id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            throw new ResourceNotFoundException(EXC_MESSAGE_PATTERN.formatted(id));
        }
    }

    @Override
    public void deleteAllByIds(List<UUID> ids) {
        repository.deleteAllById(ids);
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }

    private Client findByIdOrThrow(UUID id) {
        return repository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(EXC_MESSAGE_PATTERN.formatted(id))
                );
    }
}
