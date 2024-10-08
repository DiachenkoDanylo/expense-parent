package com.example.webapp.service;
/*  expense-parent
    05.08.2024
    @author DiachenkoDanylo
*/

import com.example.webapp.model.HelpTicketDTO;
import com.example.webapp.model.MessageSentEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public NotificationService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void notifyUser(String username, MessageSentEvent message) {
        messagingTemplate.convertAndSendToUser(username, "/queue/messages", message);
    }

    public void notifyUserWithHelpTicket(HelpTicketDTO helpTicketDTO) {
        messagingTemplate.convertAndSendToUser(helpTicketDTO.getUser(), "/queue/helpTicket", helpTicketDTO);
    }

    public void notifyAll(MessageSentEvent message) {
        messagingTemplate.convertAndSend("/topic/messages", message);
    }
}
