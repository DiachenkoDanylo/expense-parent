package com.example.managerapp.service.manager;
/*  expense-parent
    04.08.2024
    @author DiachenkoDanylo
*/

import com.example.managerapp.model.MessageSentEvent;
import lombok.AllArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class NotificationService {

    private final SimpMessagingTemplate messagingTemplate;


    public void notifyUser(String username, MessageSentEvent message) {
        messagingTemplate.convertAndSendToUser(username, "/queue/messages", message);
    }

    public void notifyAll(MessageSentEvent message) {
        messagingTemplate.convertAndSend("/topic/messages", message);
    }
}