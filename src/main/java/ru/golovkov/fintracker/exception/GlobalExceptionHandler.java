package ru.golovkov.fintracker.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ExceptionResponse handleResourceNotFoundException(ResourceNotFoundException e, WebRequest webRequest) {
        return new ExceptionResponse(webRequest, NOT_FOUND, e.getMessage());
    }

    @ResponseStatus(CONFLICT)
    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ExceptionResponse handleResourceAlreadyExistsException(ResourceAlreadyExistsException e, WebRequest webRequest) {
        return new ExceptionResponse(webRequest, CONFLICT, e.getMessage());
    }

    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ResourceIOException.class)
    public ExceptionResponse handleResourceIOException(ResourceIOException e, WebRequest webRequest) {
        return new ExceptionResponse(webRequest, INTERNAL_SERVER_ERROR, e.getMessage());
    }
}
