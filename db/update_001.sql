create table passport
(
    id       serial primary key not null,
    series   integer not null,
    number   integer not null,
    expiration_date DATE
);
insert into passport (series, number, expiration_date)
values (1111, 123456, '2021-12-12');