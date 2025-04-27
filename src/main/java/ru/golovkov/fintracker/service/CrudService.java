package ru.golovkov.fintracker.service;

import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CrudService<ID, DTO> {

    DTO create(DTO dto);

    List<DTO> createAll(List<DTO> dtos);

    DTO getById(ID id);

    List<DTO> getAll(Pageable pageable);

    DTO updateById(ID id, DTO dto);

    void deleteById(ID id);

    void deleteAllByIds(List<ID> ids);

    void deleteAll();
}