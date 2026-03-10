create table responses(
    id bigint not null auto_increment,
    message varchar(255) not null ,
    solution varchar(100) not null,
    creationDate TIMESTAMP not null,

    author_id bigint not null,
    topic_id bigint not null,

    constraint fk_author_response foreign key(author_id) references users(id),
    constraint fk_topic_response foreign key(topic_id) references topics(id),
    primary key(id)
);