package ru.golovkov.finparser.utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ru.golovkov.finparser.exception.ResourceIOException;
import ru.golovkov.finparser.exception.ResourceNotFoundException;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;

@Component
public class ParsingUtils {

    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public Sheet getSheet(MultipartFile multipartFile) {
        Workbook workbook;
        try (InputStream inputStream = multipartFile.getInputStream()) {
            workbook = new XSSFWorkbook(inputStream);
        } catch (IOException e) {
            throw new ResourceIOException("File " + multipartFile.getOriginalFilename() + " processing exception: ", e);
        }
        return workbook.getSheetAt(0);
    }

    public String getCellByNumAndRowNumFromSheet(Sheet sheet, Integer rowNum, Integer cellNum) {
        Row row = Optional
                .of(sheet.getRow(rowNum))
                .orElseThrow(() -> new ResourceNotFoundException("No row was found with number " + rowNum, null));
        Cell cell = Optional
                .of(row.getCell(cellNum))
                .orElseThrow(() -> new ResourceNotFoundException("No cell was found with number " + cellNum, null));
        return cell.toString();
    }

    public String getCellByNumFromRow(Row row, Integer cellNum) {
        return row.getCell(cellNum).toString();
    }

    public LocalDate parseRuFormatDate(String dateString) {
        try {
            return LocalDate.parse(dateString, FORMATTER);
        } catch (DateTimeParseException e) {
            throw new ResourceIOException("Date format exception: ", e);
        }
    }

    public BigDecimal parseAmount(String amountString) {
        try {
            String amount = amountString
                    .replaceAll("[^\\d,.-]", "")
                    .replace(",", ".");
            return new BigDecimal(amount);
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
            throw new ResourceIOException("Amount format exception: ", e);
        }
    }
}
