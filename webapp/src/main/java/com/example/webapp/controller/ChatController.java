package com.example.webapp.controller;
/*  expense-parent
    02.08.2024
    @author DiachenkoDanylo
*/

//import com.example.webapp.service.KafkaMessageProducer;
import com.example.webapp.model.MessageSentEvent;
import com.example.webapp.model.RequestMessageList;
import com.example.webapp.service.Producer;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/app")
@AllArgsConstructor
public class ChatController {

    private final Producer producer;

    @MessageMapping("/sendMessage")
    @SendTo("/topic/messages")
    public MessageSentEvent send(MessageSentEvent chatmessage) {
        System.out.println("controller");
        producer.sendOrderEvent(chatmessage);
        return chatmessage;
    }

//    @MessageMapping("/requestMessageList")
//    @SendTo("/topic/requestList")
//    public RequestMessageList requestList(RequestMessageList chatmessage) {
//        System.out.println("get request");
////        producer.sendListRequest(chatmessage);
//        return chatmessage;
//    }


}