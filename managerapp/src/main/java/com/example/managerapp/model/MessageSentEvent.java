package com.example.managerapp.model;
/*  expense-parent
    04.08.2024
    @author DiachenkoDanylo
*/

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageSentEvent {

    private String sendFrom;
    private String message;
    private String sendTo;
}
