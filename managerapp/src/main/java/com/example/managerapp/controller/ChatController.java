package com.example.managerapp.controller;
/*  expense-parent
    04.08.2024
    @author DiachenkoDanylo
*/

//import com.example.webapp.service.KafkaMessageProducer;
//import com.example.webapp.model.Message;
//import com.example.webapp.service.Producer;
import com.example.managerapp.model.AssignMessage;
import com.example.managerapp.model.HelpTicket;
import com.example.managerapp.model.MessageSentEvent;
import com.example.managerapp.service.manager.HelpTicketService;
import com.example.managerapp.service.manager.KafkaMessagingService;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/app")
@AllArgsConstructor
public class ChatController {

    private final KafkaMessagingService kafkaMessagingService;
    private final HelpTicketService helpTicketService;


    @MessageMapping("/sendMessage")
    @SendTo("/topic/messages")
    public MessageSentEvent send(MessageSentEvent messageSentEvent) {
        System.out.println(messageSentEvent.toString()+ " SENDING MESSAGE");
//        notificationService.notifyUser(messageSentEvent.getSendFrom(),messageSentEvent);
        HelpTicket helpTicket = helpTicketService.getTicketByUser(messageSentEvent.getSendTo()).get();
        helpTicketService.addMessage(helpTicket.getId(),messageSentEvent);
        kafkaMessagingService.sendOrder(messageSentEvent);
        return messageSentEvent;
    }

    @MessageMapping("/assign")
    @SendTo("/topic/messages")
    public AssignMessage assign(AssignMessage message) {
        helpTicketService.assignManager(message);
        System.out.println("assign to "+ message);
        return message;
    }


}