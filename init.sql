create table location
(
    id           serial
        constraint location_pk
            primary key,
    city         varchar(50) not null,
    street       varchar(50) not null,
    house_number varchar(50) not null,
    unique (city, street, house_number)
);

create table organizer
(
    id         serial
        constraint organizer_pk
            primary key,
    name       varchar(50) not null,
    surname    varchar(50) not null,
    patronymic varchar(50) not null,
    unique (name, surname, patronymic)
);

create table event
(
    id           serial
        constraint event_pk
            primary key,
    date         timestamp    not null,
    subject      varchar(70)  not null,
    description  varchar(255) not null,
    location_id  int          not null
        constraint event_location_id_fk
            references location,
    organizer_id int          not null
        constraint event_organizer_id_fk
            references organizer,
    unique (date, subject, description)
);

create unique index event_location_id_uindex
    on event (location_id);