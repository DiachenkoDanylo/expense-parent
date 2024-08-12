package com.example.managerapp.service.manager;

import com.example.managerapp.model.HelpTicket;
import com.example.managerapp.model.HelpTicketDTO;
import com.example.managerapp.model.MessageSentEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/*  expense-parent
    12.08.2024
    @author DiachenkoDanylo
*/
@Slf4j
@Service
@AllArgsConstructor
public class Consumer {

    private static final String topicCreateOrder = "chat-messages-user";
    private static final  String sendTopicRequestHelpTicket = "chat-request-help-ticket";
    private static final  String sendTopicRequestChatList = "chat-request-list";
    private static final  String sendTopicRequestNewChat = "chat-request-new";
    private static final String kafkaConsumerGroupId = "order-1";
    private static final String kafkaConsumerGroupId2 = "request-1";


    private final NotificationService notificationService;
    private final HelpTicketService helpTicketService;
    private final Producer producer;


    @Transactional
    @KafkaListener(topics = topicCreateOrder, groupId = kafkaConsumerGroupId, properties = {"spring.json.value.default.type=com.example.managerapp.model.MessageSentEvent"})
    public MessageSentEvent getMessageSentEvent(MessageSentEvent messageSentEvent) {
        HelpTicket helpTicket = helpTicketService.getTicketByTicketId(messageSentEvent.getTicketId()).get();
        messageSentEvent.setSendTo(helpTicket.getManager());
        helpTicketService.addMessage(helpTicket.getId(), messageSentEvent);
        if (helpTicket.getManager() == null) {
            helpTicket.setManager("general");
        }
        notificationService.notifyUser(helpTicket.getManager(), messageSentEvent);
        return messageSentEvent;
    }

    @KafkaListener(topics = sendTopicRequestHelpTicket, groupId = kafkaConsumerGroupId2, properties = {"spring.json.value.default.type=com.example.managerapp.model.MessageSentEvent"})
    public MessageSentEvent getRequestHelpTicket(MessageSentEvent messageSentEvent) {
        for (HelpTicketDTO dto : helpTicketService.getHelpTicketDtos(messageSentEvent.getSendFrom())) {
            producer.sendHelpTicketDtoToUser(dto, messageSentEvent.getSendFrom());
        }
        return messageSentEvent;
    }

    @KafkaListener(topics = sendTopicRequestChatList, groupId = kafkaConsumerGroupId2, properties = {"spring.json.value.default.type=com.example.managerapp.model.MessageSentEvent"})
    public MessageSentEvent getRequestChatList(MessageSentEvent messageSentEvent) {
        System.out.println("REQUEST FOR CHAT LIST");
        String user = helpTicketService.getTicketByTicketId(messageSentEvent.getSendTo()).get().getUser();
        helpTicketService.getMessages(messageSentEvent.getSendTo()).forEach(message -> producer.sendOrderList(message,user));
        return messageSentEvent;
    }

    @KafkaListener(topics = sendTopicRequestNewChat, groupId = kafkaConsumerGroupId2, properties = {"spring.json.value.default.type=com.example.managerapp.model.MessageSentEvent"})
    public MessageSentEvent getRequestNewChat(MessageSentEvent messageSentEvent) {
        helpTicketService.createTicket(messageSentEvent.getSendFrom());
        return messageSentEvent;
    }

}
