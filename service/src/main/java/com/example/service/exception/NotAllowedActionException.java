package com.example.service.exception;
/*  expense-parent
    26.06.2024
    @author DiachenkoDanylo
*/

public class NotAllowedActionException extends RuntimeException {
    public NotAllowedActionException(String message) {
        super(message);
    }
}

