package com.example.managerapp.controller;
/*  expense-parent
    04.08.2024
    @author DiachenkoDanylo
*/

import com.example.managerapp.model.AssignMessage;
import com.example.managerapp.model.HelpTicket;
import com.example.managerapp.model.MessageSentEvent;
import com.example.managerapp.service.manager.HelpTicketService;
import com.example.managerapp.service.manager.Producer;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/app")
@AllArgsConstructor
public class WSController {

    private final HelpTicketService helpTicketService;
    private final Producer producer;


    @MessageMapping("/sendMessage")
    @SendTo("/topic/messages")
    public MessageSentEvent send(MessageSentEvent messageSentEvent) {
        HelpTicket helpTicket = helpTicketService.getTicketByTicketId(messageSentEvent.getTicketId()).get();
        helpTicketService.addMessage(helpTicket.getId(),messageSentEvent);
        producer.sendOrder(messageSentEvent);
        return messageSentEvent;
    }

    @MessageMapping("/assign")
    @SendTo("/topic/messages")
    public AssignMessage assign(AssignMessage message) {
        helpTicketService.assignManager(message);
        return message;
    }


}