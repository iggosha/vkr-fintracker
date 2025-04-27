package ru.golovkov.finparser.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class ClientDto implements Serializable {

    private String name;
}