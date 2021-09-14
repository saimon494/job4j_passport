package ru.job4j.passport.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import ru.job4j.passport.domain.Passport;
import ru.job4j.passport.service.PassportService;

import java.util.List;

@RestController
@RequestMapping("/passport")
public class PassportController {

    private final PassportService service;

    public PassportController(PassportService service) {
        this.service = service;
    }

    @PostMapping("/save")
    public ResponseEntity<Passport> save(@RequestBody Passport passport) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(service.save(passport));
    }

    @PutMapping("/update{id}")
    public ResponseEntity<Passport> update(@PathVariable int id, @RequestBody Passport passport) {
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(service.update(id, passport));
    }

    @DeleteMapping("/delete{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        boolean status = service.delete(id);
        return ResponseEntity.status(status ? HttpStatus.OK : HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/find{series}")
    public ResponseEntity<List<Passport>> findBySeries(@PathVariable int series) {
        var passportList = service.findBySeries(series);
        return ResponseEntity
                .status(passportList.size() != 0 ? HttpStatus.OK : HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(passportList);
    }

    @GetMapping("/find")
    public ResponseEntity<List<Passport>> findAll() {
        var passportList = service.findAll();
        return ResponseEntity
                .status(passportList.size() != 0 ? HttpStatus.OK : HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(passportList);
    }

    @GetMapping("/unavailable")
    public ResponseEntity<List<Passport>> findUnavailable() {
        var passportList = service.findExpired();
        return ResponseEntity
                .status(passportList.size() != 0 ? HttpStatus.OK : HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(passportList);
    }

    @GetMapping("/find-replaceable")
    public ResponseEntity<List<Passport>> findReplaceable() {
        var passportList = service.findReplaceable();
        return ResponseEntity
                .status(passportList.size() != 0 ? HttpStatus.OK : HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(passportList);
    }
}
