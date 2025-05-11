package ru.golovkov.fintracker.exception;

import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@ResponseStatus(INTERNAL_SERVER_ERROR)
@NoArgsConstructor
public class ResourceIOException extends RuntimeException {

    public ResourceIOException(String message) {
        super(message);
    }

    public ResourceIOException(String message, Exception e) {
        super(message, e);
    }
}

