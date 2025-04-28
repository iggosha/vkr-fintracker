package ru.golovkov.fintracker.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.golovkov.fintracker.dto.CategoryDto;
import ru.golovkov.fintracker.exception.ResourceNotFoundException;
import ru.golovkov.fintracker.mapper.CategoryMapper;
import ru.golovkov.fintracker.model.Category;
import ru.golovkov.fintracker.repository.CategoryRepository;
import ru.golovkov.fintracker.service.CategoryService;

import java.util.List;
import java.util.UUID;

@Transactional
@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository repository;
    private final CategoryMapper mapper;
    private static final String EXC_MESSAGE_PATTERN = "Category with id %s was not found";

    @Override
    public CategoryDto create(CategoryDto dto) {
        return null;
    }

    @Override
    public CategoryDto getById(UUID id) {
        return mapper.mapToDtoFromEntity(findByIdOrThrow(id));
    }

    @Override
    public List<CategoryDto> getAll() {
        return mapper.mapToDtosFromEntities(repository.findAll());
    }

    @Override
    public CategoryDto updateById(UUID id, CategoryDto dto) {
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

    private Category findByIdOrThrow(UUID id) {
        return repository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(EXC_MESSAGE_PATTERN.formatted(id))
                );
    }
}
