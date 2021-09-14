package ru.job4j.passport.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.job4j.passport.domain.Passport;

import java.util.Date;
import java.util.List;

public interface PassportRepository extends CrudRepository<Passport, Integer> {

    List<Passport> findPassportBySeries(int series);

    @Query("select p from Passport p where p.expirationDate<=current_date")
    List<Passport> findExpiredPassport();

    @Query("select p from Passport p where p.expirationDate<=:after3months "
            + "and p.expirationDate>current_date")
    List<Passport> findExpiredPassportAfter3Months(@Param("after3months") Date newDate);
}
