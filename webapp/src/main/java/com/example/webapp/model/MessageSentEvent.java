package com.example.webapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/*  expense-parent
    04.08.2024
    @author DiachenkoDanylo
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageSentEvent {

    private String sendFrom;
    private String message;
    private String sendTo;


//    private LocalDateTime timestamp;


}
