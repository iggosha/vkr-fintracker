package ru.golovkov.finparser.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class AccountDto implements Serializable {

    private String id;
    private LocalDate creationDate;
    private String type;
}