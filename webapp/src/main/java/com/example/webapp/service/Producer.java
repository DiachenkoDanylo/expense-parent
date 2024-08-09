package com.example.webapp.service;
/*  expense-parent
    04.08.2024
    @author DiachenkoDanylo
*/

import com.example.webapp.model.MessageSentEvent;
import com.example.webapp.model.RequestMessageList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class Producer {

    private final KafkaMessagingService kafkaMessagingService;
    private final ModelMapper modelMapper;


    public MessageSentEvent sendOrderEvent(MessageSentEvent message) {
        kafkaMessagingService.sendOrder(message);
        System.out.println("Send message from producer : \n"+ message);
        return message;
    }

//    public RequestMessageList sendListRequest(RequestMessageList message) {
//        kafkaMessagingService.sendMessageList(message);
//        System.out.println("Send message to get List : \n"+ message);
//        return message;
//    }



}
