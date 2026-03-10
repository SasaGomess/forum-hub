create table topics(

    id bigint not null auto_increment,
    tittle varchar(100) not null unique,
    message varchar(255) not null unique,
    creationDate TIMESTAMP not null,
    status enum('AGUARDANDO_RESPOSTA', 'RESPONDIDO'),
    author_id bigint not null,
    course_id bigint not null,

    constraint fk_author_topic foreign key(author_id) references users(id),
    constraint fk_course_topic foreign key(course_id) references courses(id),
    primary key(id)

);