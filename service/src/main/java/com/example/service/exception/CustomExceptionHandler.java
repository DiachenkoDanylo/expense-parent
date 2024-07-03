package com.example.service.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/*  expense-parent
    06.06.2024
    @author DiachenkoDanylo
*/
@RestControllerAdvice
@Slf4j
public class CustomExceptionHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseError handle(NotFoundException exception) {
        log.error(exception.getMessage(), exception);
        return new ResponseError(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseError handleDuplicate(DuplicateException exception) {
        log.error(exception.getMessage(), exception);
        return new ResponseError(exception.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseError handleNotAllowedAction(NotAllowedActionException exception) {
        log.error(exception.getMessage(), exception);
        return new ResponseError(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
