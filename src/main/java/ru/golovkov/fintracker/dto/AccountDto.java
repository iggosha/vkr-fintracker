package ru.golovkov.fintracker.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class AccountDto {

    private String id;
    private LocalDate creationDate;
    private String type;
    private UUID clientId;
}