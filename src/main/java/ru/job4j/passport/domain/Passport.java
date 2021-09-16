package ru.job4j.passport.domain;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "passport")
public class Passport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int series;
    private int number;
    private Date expirationDate;

    public static Passport of(int series, int number, Date expirationDate) {
        var passport = new Passport();
        passport.series = series;
        passport.number = number;
        passport.expirationDate = expirationDate;
        return passport;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSeries() {
        return series;
    }

    public void setSeries(int series) {
        this.series = series;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Passport passport = (Passport) o;
        return id == passport.id && series == passport.series && number == passport.number;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, series, number);
    }

    @Override
    public String toString() {
        return "Passport{"
                + "id=" + id
                + ", series=" + series
                + ", number=" + number
                + ", expirationDate=" + expirationDate
                + '}';
    }
}
