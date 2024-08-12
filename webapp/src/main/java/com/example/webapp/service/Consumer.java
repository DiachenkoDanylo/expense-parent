package com.example.webapp.service;
/*  expense-parent
    05.08.2024
    @author DiachenkoDanylo
*/

import com.example.webapp.model.HelpTicketDTO;
import com.example.webapp.model.MessageSentEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@AllArgsConstructor
public class Consumer {

    private static final String topicMessage = "chat-messages-manager";
    private static final String kafkaConsumerGroupId = "order-2";
    private static final String kafkaConsumerGroupId2 = "request-2";
    private static final  String sendClientTopicRequest = "chat-messages-request";
    private static final String sendClientTopicRequestList = "chat-messages-manager-list";
    private final NotificationService notificationService;


    @Transactional
    @KafkaListener(topics = topicMessage, groupId = kafkaConsumerGroupId, properties = {"spring.json.value.default.type=com.example.webapp.model.MessageSentEvent"})
    public MessageSentEvent getMessage(MessageSentEvent messageSentEvent) {
        notificationService.notifyUser(messageSentEvent.getSendTo(),messageSentEvent);
        return messageSentEvent;
    }

    @Transactional
    @KafkaListener(topics = sendClientTopicRequestList, groupId = kafkaConsumerGroupId, properties = {"spring.json.value.default.type=com.example.webapp.model.MessageSentEvent"})
    public MessageSentEvent getMessageList(MessageSentEvent messageSentEvent) {
        if(messageSentEvent.getSendFrom().contains("manager")){
            notificationService.notifyUser(messageSentEvent.getSendTo(),messageSentEvent);
        }else{
            notificationService.notifyUser(messageSentEvent.getSendFrom(),messageSentEvent);
        }
        return messageSentEvent;
    }

    @Transactional
    @KafkaListener(topics = sendClientTopicRequest, groupId = kafkaConsumerGroupId2, properties = {"spring.json.value.default.type=com.example.webapp.model.HelpTicketDTO"})
    public HelpTicketDTO getHelpTicketDto(HelpTicketDTO helpTicketDTO) {
        notificationService.notifyUserWithHelpTicket( helpTicketDTO);
        return helpTicketDTO;
    }
}
