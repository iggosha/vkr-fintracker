package ru.golovkov.fintracker.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.golovkov.fintracker.dto.parsed.ParsedEntitiesDto;
import ru.golovkov.fintracker.util.FinparserClient;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sheet-files")
public class FinparserController {

    private final FinparserClient finparserClient;

    @PostMapping(value = "/parsed-entities", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ParsedEntitiesDto parse(@RequestPart MultipartFile sheetFile) {
        return finparserClient.getParsedEntities(sheetFile);
    }
}
