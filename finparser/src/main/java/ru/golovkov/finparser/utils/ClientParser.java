package ru.golovkov.finparser.utils;

import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.stereotype.Component;
import ru.golovkov.finparser.dto.ClientDto;

@Component
@RequiredArgsConstructor
public class ClientParser {

    private final ParsingUtils parsingUtils;

    public ClientDto parse(Sheet sheet) {
        return new ClientDto(parseName(sheet));
    }

    private String parseName(Sheet sheet) {
        return parsingUtils.getCellByNumAndRowNumFromSheet(sheet, 12, 2);
    }
}
