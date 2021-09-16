package ru.job4j.passport.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import ru.job4j.passport.domain.Passport;

@Service
public class PassportConsumerService {

    @KafkaListener(topics = "passport")
    @Payload(required = false)
    public void printMessage(ConsumerRecord<String, Passport> input) {
        System.out.println("Passports to replace: " + input.value());
    }
}
