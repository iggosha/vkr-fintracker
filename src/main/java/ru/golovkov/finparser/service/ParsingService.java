package ru.golovkov.finparser.service;

import org.springframework.web.multipart.MultipartFile;
import ru.golovkov.finparser.dto.AccountDto;
import ru.golovkov.finparser.dto.ClientDto;
import ru.golovkov.finparser.dto.RowDto;
import ru.golovkov.finparser.dto.SheetEntitiesDto;

import java.util.List;

public interface ParsingService {

    List<RowDto> parseRows(MultipartFile sheetFile);

    String parseCell(MultipartFile sheetFile, Integer rowNum, Integer cellNum);

    AccountDto parseAccount(MultipartFile sheetFile);

    ClientDto parseClient(MultipartFile sheetFile);

    SheetEntitiesDto parseSheetEntities(MultipartFile sheetFile);
}
