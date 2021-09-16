package ru.job4j.passport.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ru.job4j.passport.service.PassportService;

@Controller
public class KafkaPassportController {

    @Autowired
    private PassportService service;

    @GetMapping("/check")
    public ResponseEntity<Void> checkPassports() {
        service.checkPassportByDate();
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
