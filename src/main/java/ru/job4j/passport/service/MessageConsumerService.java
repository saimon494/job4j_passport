package ru.job4j.passport.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class MessageConsumerService {

    @KafkaListener(topics = "msg", groupId = "app.1")
    public void getMessage(String msg) {
        System.out.println("Received: " + msg);
    }
}
