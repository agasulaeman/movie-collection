-- auto-generated definition
create table movie_table_collection
(
    id               serial
        primary key,
    created_by       varchar(255),
    is_deleted       boolean,
    last_modified_by varchar(255),
    genres           varchar(50)  not null,
    director         varchar(200),
    summary          varchar(100) not null,
    title            varchar(200) not null,
    created_at       timestamp,
    modified_at      timestamp
);

alter table movie_table_collection
    owner to postgres;