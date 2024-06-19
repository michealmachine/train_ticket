create table gith.node
(
    id   int         not null
        primary key,
    name varchar(10) null,
    constraint name
        unique (name)
);

