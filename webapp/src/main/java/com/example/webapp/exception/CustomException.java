package com.example.webapp.exception;
/*  expense-parent
    13.06.2024
    @author DiachenkoDanylo
*/

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
public class CustomException extends RuntimeException {
    // Getters and Setters



    private String message;
    private String status;
    private String time;

    @JsonCreator
    public CustomException(@JsonProperty("message") String message,
                           @JsonProperty("status") String status,
                           @JsonProperty("time") String time) {
        super(message);
        this.message = message;
        this.status = status;
        this.time = time;
    }

    public CustomException(CustomException e) {
        this.message = e.getMessage();
        this.status =e.getStatus();
        this.time = e.getTime();
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "CustomException{" +
                "message='" + message + '\'' +
                ", status='" + status + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}