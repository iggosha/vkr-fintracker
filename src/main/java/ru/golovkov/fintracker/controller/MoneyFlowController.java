package ru.golovkov.fintracker.controller;

import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.golovkov.fintracker.dto.MoneyFlowDto;
import ru.golovkov.fintracker.service.MoneyFlowService;

import java.util.List;
import java.util.UUID;

import static org.springframework.data.domain.Sort.Direction.DESC;

@RestController
@RequiredArgsConstructor
public class MoneyFlowController {

    private final MoneyFlowService service;

    @PostMapping(value = "/flows/parsed-entities", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void parse(@RequestPart MultipartFile sheetFile) {
        service.createFromFile(sheetFile);
    }

    @PostMapping("/flows")
    public MoneyFlowDto create(@RequestBody MoneyFlowDto moneyFlowDto, @RequestParam String accountId) {
        return service.create(moneyFlowDto, accountId);
    }

    @GetMapping("/clients/{clientId}/flows")
    public Page<MoneyFlowDto> getAllByClientId(
            @PathVariable UUID clientId,
            @RequestParam(required = false) UUID categoryId,
            @ParameterObject @PageableDefault(sort = {"date"}, direction = DESC) Pageable pageable
    ) {
        return service.getAllByClientId(clientId, categoryId, pageable);
    }

    @GetMapping("/accounts/{accountId}/flows")
    public List<MoneyFlowDto> getAllByAccountId(
            @PathVariable String accountId,
            @RequestParam(required = false) UUID categoryId,
            @ParameterObject @PageableDefault(sort = {"date"}, direction = DESC) Pageable pageable
    ) {
        return service.getAllByAccountId(accountId, categoryId, pageable);
    }

    @GetMapping("/flows/{id}")
    public MoneyFlowDto getById(@PathVariable String id) {
        return service.getById(id);
    }

    @PutMapping("/flows/{id}")
    public MoneyFlowDto update(@PathVariable String id, @RequestBody MoneyFlowDto moneyFlowDto) {
        return service.updateById(id, moneyFlowDto);
    }

    @DeleteMapping("/flows/{id}")
    public void deleteById(@PathVariable String id) {
        service.deleteById(id);
    }

    @DeleteMapping("/flows")
    public void deleteAll() {
        service.deleteAll();
    }
}