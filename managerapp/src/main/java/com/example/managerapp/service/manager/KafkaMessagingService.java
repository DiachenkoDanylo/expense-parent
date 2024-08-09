package com.example.managerapp.service.manager;
/*  expense-parent
    04.08.2024
    @author DiachenkoDanylo
*/

import com.example.managerapp.controller.ChatController;
import com.example.managerapp.model.HelpTicket;
import com.example.managerapp.model.MessageSentEvent;
import com.example.managerapp.model.RequestMessageList;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class KafkaMessagingService {


    private static final String sendClientTopic = "chat-messages-manager";
    private final KafkaTemplate<String , Object> kafkaTemplate;
    private static final String topicCreateOrder = "chat-messages-user";
    private static final String kafkaConsumerGroupId = "order-1";
    private final NotificationService notificationService;
    private final HelpTicketService helpTicketService;


    @Transactional
    @KafkaListener(topics = topicCreateOrder, groupId = kafkaConsumerGroupId, properties = {"spring.json.value.default.type=com.example.managerapp.model.MessageSentEvent"})
    public MessageSentEvent getMessageSentEvent(MessageSentEvent messageSentEvent) {
        System.out.println("inside kafka listener, message sent event: \n"+messageSentEvent.toString());
        if(helpTicketService.getTicketByUser(messageSentEvent.getSendFrom()).isEmpty()) {
            HelpTicket helpTicket = helpTicketService.createTicket(messageSentEvent.getSendFrom());
            helpTicketService.addMessage(helpTicket.getId(),messageSentEvent);
        }else{
            HelpTicket helpTicket = helpTicketService.getTicketByUser(messageSentEvent.getSendFrom()).get();
            messageSentEvent.setSendTo(helpTicket.getManager());
            helpTicketService.addMessage(helpTicket.getId(),messageSentEvent);
            System.out.println("inside kafka listener, message sent event: \n"+messageSentEvent.toString());
            notificationService.notifyUser(helpTicket.getManager(),messageSentEvent);

        }
        return messageSentEvent;
    }

    public void sendOrder(MessageSentEvent messageSentEvent) {
        kafkaTemplate.send(sendClientTopic, messageSentEvent.getSendTo(), messageSentEvent);
    }

}
