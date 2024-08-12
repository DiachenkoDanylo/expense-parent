package com.example.managerapp.model;
/*  expense-parent
    04.08.2024
    @author DiachenkoDanylo
*/

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageSentEvent {

    private String sendFrom;
    private String message;
    private String sendTo;
    private String ticketId;
}
