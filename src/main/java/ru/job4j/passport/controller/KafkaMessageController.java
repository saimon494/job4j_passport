package ru.job4j.passport.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;

@RestController
public class KafkaMessageController {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @PostMapping("/")
    public String sendMsg(@PathParam("message") String message) {
        kafkaTemplate.send("msg", message);
        return "Message send";
    }
}
