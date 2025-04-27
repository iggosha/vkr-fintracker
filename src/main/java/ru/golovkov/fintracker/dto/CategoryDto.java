package ru.golovkov.fintracker.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link ru.golovkov.fintracker.model.Category}
 */
@Data
public class CategoryDto implements Serializable {

    private UUID id;
    private String name;
    private String additionalInfo;
}