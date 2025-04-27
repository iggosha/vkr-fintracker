package ru.golovkov.finparser.utils;

import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Component;
import ru.golovkov.finparser.dto.MoneyFlowDto;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class MoneyFlowParser {

    private final ParsingUtils parsingUtils;

    public MoneyFlowDto parse(Row row) {
        return MoneyFlowDto
                .builder()
                .id(parseId(row))
                .date(parseDate(row))
                .categoryName(parseCategoryName(row))
                .description(parseDescription(row))
                .amount(parseAmount(row))
                .additionalInfo(parseStatus(row))
                .build();
    }

    private LocalDate parseDate(Row row) {
        return parsingUtils.parseRuFormatDate(parsingUtils.getCellByNumFromRow(row, 0));
    }

    public String parseId(Row row) {
        return parsingUtils.getCellByNumFromRow(row, 3);
    }

    private String parseCategoryName(Row row) {
        return parsingUtils.getCellByNumFromRow(row, 4);
    }

    private String parseDescription(Row row) {
        return parsingUtils.getCellByNumFromRow(row, 11);
    }

    private BigDecimal parseAmount(Row row) {
        return parsingUtils.parseAmount(parsingUtils.getCellByNumFromRow(row, 12));
    }

    private String parseStatus(Row row) {
        return parsingUtils.getCellByNumFromRow(row, 14);
    }
}