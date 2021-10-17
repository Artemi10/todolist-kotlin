set time zone 'Europe/Moscow';

create table users
(
    id         bigserial
        constraint users_pk
            primary key,
    login      varchar(45) not null,
    birth_date date        not null
);

alter table users
    owner to postgres;

create unique index users_id_uindex
    on users (id);

create unique index users_login_uindex
    on users (login);

create table tasks
(
    id       bigserial
        constraint tasks_pk
            primary key,
    title    varchar(45) not null,
    text  varchar(255),
    is_ready boolean     not null,
    date     date        not null,
    user_id  bigint      not null
        constraint tasks_users_id_fk
            references users
);

alter table tasks
    owner to postgres;

create unique index tasks_id_uindex
    on tasks (id);

insert into users (id, login, birth_date)
values (1, 'devanmejia', '2003-02-03');

insert into tasks (id, title, text, is_ready, "date", user_id)
values (2, 'Homework', 'Do homework until Friday', true, '2021-10-12', 1);
insert into tasks (id, title, text, is_ready, "date", user_id)
values (3, 'Extended homework', 'Do homework until Sunday', false, '2021-10-12', 1);
insert into tasks (id, title, text, is_ready, "date", user_id)
values (4, 'Homework Again', 'Do homework again until Saturday', false, '2021-10-13', 1);
insert into tasks (id, title, text, is_ready, "date", user_id)
values (5, 'Homework Again', 'Do homework again until Saturday', false, '2021-10-13', 1);
insert into tasks (id, title, text, is_ready, "date", user_id)
values (6, 'Homework Again', 'Do homework again until Saturday', false, '2021-10-13', 1);
insert into tasks (id, title, text, is_ready, "date", user_id)
values (7, 'Homework Again', 'Do homework again until Saturday', false, '2021-10-13', 1);
