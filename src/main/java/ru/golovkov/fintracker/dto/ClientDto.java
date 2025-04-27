package ru.golovkov.fintracker.dto;

import lombok.Data;
import ru.golovkov.fintracker.model.Client;

import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link Client}
 */
@Data
public class ClientDto implements Serializable {

    private UUID id;
    private String name;
    private String additionalInfo;
}