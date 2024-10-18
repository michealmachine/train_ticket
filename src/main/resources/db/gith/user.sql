create table gith.user
(
    id       int auto_increment
        primary key,
    username varchar(50) not null,
    password varchar(255) null,
    phone    varchar(15) null,
    city     varchar(50) null,
    status   int         null,
    minister tinyint(1)  null,
    constraint username
        unique (username)
);
