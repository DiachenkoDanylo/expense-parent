package com.example.service.exception;
/*  expense-parent
    06.06.2024
    @author DiachenkoDanylo
*/

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import org.springframework.http.HttpStatusCode;

import java.time.LocalDateTime;

@Getter
public class ResponseError {

    private final HttpStatusCode status;
    private final String message;
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime time = LocalDateTime.now();

    public ResponseError(String message, HttpStatusCode status) {
        this.message = message;
        this.status = status;
    }
}
