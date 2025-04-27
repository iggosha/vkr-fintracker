package ru.golovkov.finparser.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@Data
public class ExceptionResponse {

    private String path;
    private Integer statusCode;
    private String message;
    private LocalDateTime timestamp;

    public ExceptionResponse(WebRequest webRequest, HttpStatus httpStatus, String message) {
        timestamp = LocalDateTime.now();
        path = webRequest.getDescription(true);
        statusCode = httpStatus.value();
        this.message = message;
    }
}
