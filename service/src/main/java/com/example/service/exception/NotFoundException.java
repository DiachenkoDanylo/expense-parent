package com.example.service.exception;
/*  expense-parent
    28.05.2024
    @author DiachenkoDanylo
*/

public class NotFoundException extends RuntimeException {

    public NotFoundException(String message) {
        super(message);
    }
}
