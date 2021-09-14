package ru.job4j.passport.service;

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
}
