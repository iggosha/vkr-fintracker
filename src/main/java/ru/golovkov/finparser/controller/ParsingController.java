package ru.golovkov.finparser.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.golovkov.finparser.dto.RowDto;
import ru.golovkov.finparser.service.ParsingService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/parsing")
public class ParsingController {
    private final ParsingService parsingService;

    @GetMapping(value = "/rows", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<RowDto> getRowsFromFile(@RequestParam("file") MultipartFile sheetFile) {
        return parsingService.getRows(sheetFile);
    }

    @PostMapping(value = "/rows/{rowNum}/cells/{cellNum}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String getCell(@RequestParam("file")  MultipartFile sheetFile,
                          @PathVariable Integer rowNum,
                          @PathVariable Integer cellNum) {
        return parsingService.getCell(sheetFile, rowNum, cellNum);
    }
}
