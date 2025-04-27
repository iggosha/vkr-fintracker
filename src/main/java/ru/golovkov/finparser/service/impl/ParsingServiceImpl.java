package ru.golovkov.finparser.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.golovkov.finparser.dto.RowDto;
import ru.golovkov.finparser.service.ParsingService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ParsingServiceImpl implements ParsingService {

    @Override
    public List<RowDto> getRows(MultipartFile sheetFile) {
        return List.of();
    }

    @Override
    public String getCell(MultipartFile sheetFile, Integer rowNum, Integer cellNum) {
        return "";
    }
}
