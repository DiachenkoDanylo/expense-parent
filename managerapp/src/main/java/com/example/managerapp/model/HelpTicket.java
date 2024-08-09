package com.example.managerapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/*  expense-parent
    05.08.2024
    @author DiachenkoDanylo
*/
@Data
@AllArgsConstructor
@Document(collection = "help_tickets")
public class HelpTicket {

    @Id
    private String id;
    private String user;
    private String manager;
    private boolean solved;
    private List<MessageSentEvent> chatList;

    public HelpTicket() {
        this.chatList = new ArrayList<>();
    }

    // Getters and Setters
}
