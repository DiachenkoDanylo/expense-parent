package com.example.webapp.service;
/*  expense-parent
    02.08.2024
    @author DiachenkoDanylo
*/

import com.example.webapp.model.MessageSentEvent;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Properties;

@Service
@RequiredArgsConstructor
public class KafkaMessagingService {

    private static final  String sendClientTopic = "chat-messages-user";

    private final KafkaTemplate<String , Object> kafkaTemplate;

    public void sendOrder(MessageSentEvent messageSentEvent) {
        kafkaTemplate.send(sendClientTopic, messageSentEvent.getSendTo(), messageSentEvent);
    }



}