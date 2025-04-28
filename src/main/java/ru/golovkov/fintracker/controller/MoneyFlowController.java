package ru.golovkov.fintracker.controller;

import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.golovkov.fintracker.dto.MoneyFlowDto;
import ru.golovkov.fintracker.service.MoneyFlowService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/flows")
public class MoneyFlowController {

    private final MoneyFlowService service;

    @PostMapping(value = "/parsed-entities", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void parse(@RequestPart MultipartFile sheetFile) {
        service.createFromFile(sheetFile);
    }

    @GetMapping
    public List<MoneyFlowDto> getAll(
            @ParameterObject @PageableDefault(sort = {"date"}, direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return service.getAll(pageable);
    }
}
