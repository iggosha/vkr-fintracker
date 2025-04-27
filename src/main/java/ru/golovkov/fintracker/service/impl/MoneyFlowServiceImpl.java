package ru.golovkov.fintracker.service.impl;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.golovkov.fintracker.dto.MoneyFlowDto;
import ru.golovkov.fintracker.service.MoneyFlowService;

import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
@Slf4j
public class MoneyFlowServiceImpl implements MoneyFlowService {

    @Override
    public MoneyFlowDto create(MoneyFlowDto dto) {
        return null;
    }

    @Override
    public List<MoneyFlowDto> createAll(List<MoneyFlowDto> dtos) {
        return List.of();
    }

    @Override
    public MoneyFlowDto getById(String id) {
        return null;
    }

    @Override
    public List<MoneyFlowDto> getAll(Pageable pageable) {
        return List.of();
    }

    @Override
    public MoneyFlowDto updateById(String id, MoneyFlowDto dto) {
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
