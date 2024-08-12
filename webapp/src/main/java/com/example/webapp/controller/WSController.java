package com.example.webapp.controller;
/*  expense-parent
    02.08.2024
    @author DiachenkoDanylo
*/

//import com.example.webapp.service.KafkaMessageProducer;
import com.example.webapp.model.MessageSentEvent;

import com.example.webapp.service.Producer;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/app")
@AllArgsConstructor
public class WSController {

    private final Producer producer;

    @MessageMapping("/sendMessage")
    @SendTo("/topic/messages")
    public MessageSentEvent sendMessage(MessageSentEvent chatmessage) {
        producer.sendMessage(chatmessage);
        return chatmessage;
    }

    @MessageMapping("/request")
    @SendTo("/topic/request")
    public MessageSentEvent getRequest(MessageSentEvent req){
        switch (req.getMessage()){
            case "REQUEST_FOR_CHATLIST":
                producer.sendRequestForChatList(req);
                break;
            case "REQUEST_CHAT":
                producer.sendRequestForHelpTicketDto(req);
                break;
            case "NEW_CHAT":
                producer.sendRequestForOpenNewChat(req);
                break;
        }
        return req;
    }

}