package ru.golovkov.finparser.utils;

import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.stereotype.Component;
import ru.golovkov.finparser.dto.AccountDto;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class AccountParser {

    private final ParsingUtils parsingUtils;

    public AccountDto parse(Sheet sheet) {
        return new AccountDto(parseId(sheet), parseCreationDate(sheet), parseType(sheet));
    }

    public String parseId(Sheet sheet) {
        return parsingUtils.getCellByNumAndRowNumFromSheet(sheet, 7, 2);
    }

    private LocalDate parseCreationDate(Sheet sheet) {
        String cellValue = parsingUtils.getCellByNumAndRowNumFromSheet(sheet, 8, 2);
        return parsingUtils.parseRuFormatDate(cellValue);
    }

    private String parseType(Sheet sheet) {
        return parsingUtils.getCellByNumAndRowNumFromSheet(sheet, 10, 2);
    }
}