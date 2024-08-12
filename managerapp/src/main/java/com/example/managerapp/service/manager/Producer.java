package com.example.managerapp.service.manager;
/*  expense-parent
    12.08.2024
    @author DiachenkoDanylo
*/

import com.example.managerapp.model.HelpTicketDTO;
import com.example.managerapp.model.MessageSentEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class Producer {


    private static final String sendClientTopic = "chat-messages-manager";
    private static final  String sendClientTopicRequest = "chat-messages-request";
    private static final String sendClientTopicList = "chat-messages-manager-list";
    private final KafkaTemplate<String , Object> kafkaTemplate;


    public void sendOrder(MessageSentEvent messageSentEvent) {
        System.out.println("send message from kafka manager\n"+messageSentEvent.toString());
        kafkaTemplate.send(sendClientTopic, messageSentEvent.getSendTo(), messageSentEvent);
    }

    public void sendOrderList(MessageSentEvent messageSentEvent,String username) {
        System.out.println("send message from kafka manager\n"+messageSentEvent.toString());
        kafkaTemplate.send(sendClientTopicList, username, messageSentEvent);
    }

    public void sendHelpTicketDtoToUser(HelpTicketDTO ticket, String user) {
        System.out.println("send helpticketdtos from kafka manager\n"+ticket.toString());
        kafkaTemplate.send(sendClientTopicRequest, user, ticket);
    }

}
