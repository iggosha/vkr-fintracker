package ru.golovkov.fintracker.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.golovkov.fintracker.dto.CategoryDto;
import ru.golovkov.fintracker.service.CategoryService;

import java.util.List;
import java.util.UUID;

@Transactional
@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    @Override
    public CategoryDto create(CategoryDto dto) {
        return null;
    }

    @Override
    public List<CategoryDto> createAll(List<CategoryDto> dtos) {
        return List.of();
    }

    @Override
    public CategoryDto getById(UUID id) {
        return null;
    }

    @Override
    public List<CategoryDto> getAll(Pageable pageable) {
        return List.of();
    }

    @Override
    public CategoryDto updateById(UUID id, CategoryDto dto) {
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
