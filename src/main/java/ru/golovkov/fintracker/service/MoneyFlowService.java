package ru.golovkov.fintracker.service;

import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import ru.golovkov.fintracker.dto.MoneyFlowDto;

import java.util.List;
import java.util.UUID;

public interface MoneyFlowService extends CrudService<String, MoneyFlowDto> {

    MoneyFlowDto create(MoneyFlowDto moneyFlowDto, String accountId);

    List<MoneyFlowDto> getAll(Pageable pageable);

    List<MoneyFlowDto> createAll(List<MoneyFlowDto> dtos);

    void createFromFile(MultipartFile sheetFile);

    List<MoneyFlowDto> getAllByClientId(UUID clientId, UUID categoryId, Pageable pageable);

    List<MoneyFlowDto> getAllByAccountId(String accountId, UUID categoryId, Pageable pageable);
}
