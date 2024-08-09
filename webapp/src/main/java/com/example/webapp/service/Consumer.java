package com.example.webapp.service;
/*  expense-parent
    05.08.2024
    @author DiachenkoDanylo
*/

import com.example.webapp.model.MessageSentEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class Consumer {
    private static final String topicCreateOrder = "chat-messages-manager";
    private static final String kafkaConsumerGroupId = "order-2";
    private final KafkaMessagingService kafkaMessagingService;
    private final NotificationService notificationService;
//    private final NotificationService notificationService;


    @Transactional
    @KafkaListener(topics = topicCreateOrder, groupId = kafkaConsumerGroupId, properties = {"spring.json.value.default.type=com.example.webapp.model.MessageSentEvent"})
    public MessageSentEvent getMessageSentEvent(MessageSentEvent messageSentEvent) {
        System.out.println("received message : \n" + messageSentEvent.toString());
        notificationService.notifyUser(messageSentEvent.getSendTo(),messageSentEvent);
        return messageSentEvent;
    }




}
