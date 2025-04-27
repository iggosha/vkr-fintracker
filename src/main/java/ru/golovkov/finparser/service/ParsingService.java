package ru.golovkov.finparser.service;

import org.springframework.web.multipart.MultipartFile;
import ru.golovkov.finparser.dto.RowDto;

import java.util.List;

public interface ParsingService {

    List<RowDto> getRows(MultipartFile sheetFile);

    String getCell(MultipartFile sheetFile, Integer rowNum, Integer cellNum);
}
