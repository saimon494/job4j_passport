package ru.job4j.passport.service;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.job4j.passport.domain.Passport;

import java.util.Collections;
import java.util.List;

@Service
public class PassportApiService {

    public static final String URL = "http://localhost:8080/passport_api";

    private final RestTemplate rest;

    public PassportApiService(RestTemplateBuilder builder) {
        this.rest = builder.build();
    }

    public Passport save(Passport passport) {
        return rest.postForEntity(URL, passport, Passport.class).getBody();
    }

    public boolean update(int id, Passport passport) {
        return rest.exchange(
                String.format("%s?id=%s", URL, id),
                HttpMethod.PUT,
                new HttpEntity<>(passport),
                Void.class).getStatusCode() != HttpStatus.NOT_FOUND;
    }

    public boolean delete(int id) {
        return rest.exchange(
                String.format("%s?id=%s", URL, id),
                HttpMethod.DELETE,
                HttpEntity.EMPTY,
                Void.class).getStatusCode() != HttpStatus.NOT_FOUND;
    }

    public List<Passport> findAll() {
        return getAll(URL);
    }

    public List<Passport> findBySeries(String series) {
        return getAll(String.format("%s/series?series=%s", URL, series));
    }

    public List<Passport> findExpired() {
        return getAll(String.format("%s/unavailable", URL));
    }

    public List<Passport> findReplaceable() {
        return getAll(String.format("%s/replaceable", URL));
    }

    private List<Passport> getAll(String url) {
        List<Passport> list = rest.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Passport>>() {
                }
        ).getBody();
        return list == null ? Collections.emptyList() : list;
    }
}
