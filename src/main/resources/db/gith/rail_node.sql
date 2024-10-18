create table gith.rail_node
(
    id               int      not null auto_increment,
    start_time       datetime null,
    start_station_id int      null,
    end_station_id   int      null,
    end_time         datetime null,
    price            int      null,
    primary key (id)
);
