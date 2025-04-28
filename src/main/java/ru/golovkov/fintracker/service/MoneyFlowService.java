package ru.golovkov.fintracker.service;

import org.springframework.web.multipart.MultipartFile;
import ru.golovkov.fintracker.dto.MoneyFlowDto;

public interface MoneyFlowService extends CrudService<String, MoneyFlowDto> {

    void createFromFile(MultipartFile sheetFile);
}
