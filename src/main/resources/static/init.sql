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
    content  varchar(255),
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

