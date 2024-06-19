create table gith.user
(
    id       int auto_increment
        primary key,
    username varchar(10) not null,
    password varchar(20) null,
    phone    varchar(11) null,
    city     varchar(10) null,
    status   int         null,
    minister tinyint(1)  null,
    constraint username
        unique (username)
);

