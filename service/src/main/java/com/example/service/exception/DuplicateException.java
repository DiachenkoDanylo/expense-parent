package com.example.service.exception;

import lombok.experimental.StandardException;
import org.springframework.web.bind.annotation.ExceptionHandler;

/*  expense-parent
    28.05.2024
    @author DiachenkoDanylo
*/

public class DuplicateException extends RuntimeException{
    public DuplicateException(String message) {
        super(message);
    }
}
