package ru.golovkov.fintracker.service;

import java.util.List;

public interface CrudService<ID, DTO> {

    DTO create(DTO dto);

    DTO getById(ID id);

    List<DTO> getAll();

    DTO updateById(ID id, DTO dto);

    void deleteById(ID id);

    void deleteAllByIds(List<ID> ids);

    void deleteAll();
}