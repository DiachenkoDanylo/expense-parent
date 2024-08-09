package com.example.managerapp.service.manager;
/*  expense-parent
    04.08.2024
    @author DiachenkoDanylo
*/

import com.example.managerapp.controller.ChatController;
import com.example.managerapp.model.MessageSentEvent;
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

    public void notifyAll(MessageSentEvent message) {
        messagingTemplate.convertAndSend("/topic/messages", message);
    }
}