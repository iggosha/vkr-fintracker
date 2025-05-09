package ru.golovkov.finparser.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.golovkov.finparser.dto.AccountDto;
import ru.golovkov.finparser.dto.ClientDto;
import ru.golovkov.finparser.dto.MoneyFlowDto;
import ru.golovkov.finparser.dto.ParsedEntitiesDto;
import ru.golovkov.finparser.dto.RowDto;
import ru.golovkov.finparser.exception.ResourceIOException;
import ru.golovkov.finparser.service.ParsingService;
import ru.golovkov.finparser.utils.AccountParser;
import ru.golovkov.finparser.utils.ClientParser;
import ru.golovkov.finparser.utils.MoneyFlowParser;
import ru.golovkov.finparser.utils.ParsingUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ParsingServiceImpl implements ParsingService {

    private final ParsingUtils parsingUtils;
    private final AccountParser accountParser;
    private final ClientParser clientParser;
    private final MoneyFlowParser moneyFlowParser;
    public static final int EMPTY_ROWS_AMOUNT = 20;

    @Override
    public List<RowDto> parseRows(MultipartFile sheetFile) {
        List<RowDto> rows = new ArrayList<>();
        Sheet sheet = parsingUtils.getSheet(sheetFile);
        for (Row row : sheet) {
            List<String> cells = new ArrayList<>();
            for (Cell cell : row) {
                cells.add(cell.toString());
            }
            rows.add(new RowDto(cells));
        }
        return rows;
    }

    @Override
    public String parseCell(MultipartFile sheetFile, Integer rowNum, Integer cellNum) {
        Sheet sheet = parsingUtils.getSheet(sheetFile);
        Row row = sheet.getRow(rowNum);
        return parsingUtils.getCellByNumFromRow(row, cellNum);
    }

    @Override
    public AccountDto parseAccount(MultipartFile sheetFile) {
        Sheet sheet = parsingUtils.getSheet(sheetFile);
        return accountParser.parse(sheet);
    }

    @Override
    public ClientDto parseClient(MultipartFile sheetFile) {
        Sheet sheet = parsingUtils.getSheet(sheetFile);
        return clientParser.parse(sheet);
    }

    @Override
    public ParsedEntitiesDto parseSheetEntities(MultipartFile sheetFile) {
        log.info("Got file: {}", sheetFile.getOriginalFilename());
        Sheet sheet = parsingUtils.getSheet(sheetFile);
        ParsedEntitiesDto parsedEntitiesDto = new ParsedEntitiesDto();
        parsedEntitiesDto.setClient(clientParser.parse(sheet));
        parsedEntitiesDto.setAccount(accountParser.parse(sheet));
        try {
            for (Row row : sheet) {
                if (row.getRowNum() < EMPTY_ROWS_AMOUNT) continue;
                MoneyFlowDto moneyFlowDto = moneyFlowParser.parse(row);
                parsedEntitiesDto.getMoneyFlows().add(moneyFlowDto);
            }
        } catch (ResourceIOException e) {
            log.info("Supposedly got EOF, last parsed transaction '{}'", parsedEntitiesDto.getMoneyFlows().getLast());
        }
        return parsedEntitiesDto;
    }
}
