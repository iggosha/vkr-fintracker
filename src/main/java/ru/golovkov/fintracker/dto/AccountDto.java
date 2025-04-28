package ru.golovkov.fintracker.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AccountDto {

    private String id;
    private LocalDate creationDate;
    private String type;
}