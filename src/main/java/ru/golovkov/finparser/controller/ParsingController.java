package ru.golovkov.finparser.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.golovkov.finparser.dto.AccountDto;
import ru.golovkov.finparser.dto.ClientDto;
import ru.golovkov.finparser.dto.RowDto;
import ru.golovkov.finparser.dto.ParsedEntitiesDto;
import ru.golovkov.finparser.service.ParsingService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/parsing")
public class ParsingController {

    private final ParsingService parsingService;

    @PostMapping(value = "/rows", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<RowDto> getRowsFromFile(@RequestPart("file") MultipartFile sheetFile) {
        return parsingService.parseRows(sheetFile);
    }

    @PostMapping(value = "/rows/{rowNum}/cells/{cellNum}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String getCell(@RequestPart("file") MultipartFile sheetFile,
                          @PathVariable Integer rowNum,
                          @PathVariable Integer cellNum) {
        return parsingService.parseCell(sheetFile, rowNum, cellNum);
    }

    @PostMapping(value = "/client", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ClientDto getClient(@RequestPart("file") MultipartFile sheetFile) {
        return parsingService.parseClient(sheetFile);
    }

    @PostMapping(value = "/account", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public AccountDto getAccount(@RequestPart("file") MultipartFile sheetFile) {
        return parsingService.parseAccount(sheetFile);
    }

    @PostMapping(value = "/parsed-entities", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ParsedEntitiesDto getSheetEntity(@RequestPart("file") MultipartFile sheetFile) {
        return parsingService.parseSheetEntities(sheetFile);
    }
}
