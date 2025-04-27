package ru.golovkov.fintracker.dto.parsed;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ParsedAccountDto {

    private String id;
    private LocalDate creationDate;
    private String type;
}
