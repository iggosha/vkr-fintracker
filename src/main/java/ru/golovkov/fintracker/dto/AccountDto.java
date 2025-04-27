package ru.golovkov.fintracker.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link ru.golovkov.fintracker.model.Account}
 */
@Data
public class AccountDto implements Serializable {

    private String id;
    private LocalDate creationDate;
    private String type;
}