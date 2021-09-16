package ru.job4j.passport.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.job4j.passport.domain.Passport;
import ru.job4j.passport.repository.PassportRepository;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class PassportService {

    private final PassportRepository repository;

    @Autowired
    private KafkaTemplate<Integer, Passport> kafkaTemplate;

    public PassportService(final PassportRepository repository) {
        this.repository = repository;
    }

    public Passport save(Passport passport) {
        repository.save(passport);
        return passport;
    }

    public Passport update(int id, Passport passport) {
        Passport newPassport = repository.findById(id).get();
        if (passport != null) {
            newPassport.setSeries(passport.getSeries());
            newPassport.setNumber(passport.getNumber());
            newPassport.setExpirationDate(passport.getExpirationDate());
            repository.save(newPassport);
        }
        return newPassport;
    }

    public boolean delete(int id) {
        boolean result = false;
        if (repository.findById(id).isPresent()) {
            repository.delete(repository.findById(id).get());
            result = true;
        }
        return result;
    }

    public List<Passport> findAll() {
        return StreamSupport.stream(this.repository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public List<Passport> findBySeries(int series) {
        return repository.findPassportBySeries(series);
    }

    public List<Passport> findExpired() {
        return repository.findExpiredPassport();
    }

    public List<Passport> findReplaceable() {
        var currentDate = new Date();
        long dateAfter3Months = currentDate.getTime() + 1000 * 60 * 60 * 24 * 90L;
        return repository.findExpiredPassportAfter3Months(new Date(dateAfter3Months));
    }

    @Scheduled(initialDelay = 3000, fixedDelay = 2000)
    public void checkPassportByDate() {
        if (findReplaceable().size() != 0) {
            System.out.println("Passports for replacement: ");
            for (Passport p : findReplaceable()) {
                kafkaTemplate.send("passport", p);
                System.out.println();
            }
        } else {
            System.out.println("There are no passports for replacement");
        }
    }
}
