package ru.golovkov.finparser.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class RowDto {

    private List<String> cells;
}