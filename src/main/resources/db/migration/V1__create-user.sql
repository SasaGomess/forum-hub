create table users(

    id bigint not null auto_increment,
    name varchar(100) not null unique,
    email varchar(100) not null unique,
    password varchar(255) not null,

    primary key(id)

);