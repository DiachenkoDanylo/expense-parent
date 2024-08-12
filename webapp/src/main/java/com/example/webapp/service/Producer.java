package com.example.webapp.service;
/*  expense-parent
    04.08.2024
    @author DiachenkoDanylo
*/

import com.example.webapp.model.MessageSentEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class Producer {


    private static final  String sendTopicMessage = "chat-messages-user";
    private static final  String sendTopicRequestHelpTicket = "chat-request-help-ticket";
    private static final  String sendTopicRequestChatList = "chat-request-list";
    private static final  String sendTopicRequestNewChat = "chat-request-new";

    private final KafkaTemplate<String , Object> kafkaTemplate;

    public void sendMessage(MessageSentEvent messageSentEvent) {
        kafkaTemplate.send(sendTopicMessage, messageSentEvent.getSendTo(), messageSentEvent);
    }

    public void sendRequestForHelpTicketDto(MessageSentEvent req){
        kafkaTemplate.send(sendTopicRequestHelpTicket, req.getSendTo(), req);
    }

    public void sendRequestForChatList(MessageSentEvent req){
        kafkaTemplate.send(sendTopicRequestChatList, req.getSendTo(), req);
    }


    public void sendRequestForOpenNewChat(MessageSentEvent req){
        kafkaTemplate.send(sendTopicRequestNewChat, req.getSendTo(), req);
    }
}
