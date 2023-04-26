create table member
(
    id          bigint not null auto_increment,
    hand_raised bit,
    name        varchar(255),
    role        varchar(255),
    primary key (id)
) engine=InnoDB;
create table token
(
    id         bigint not null auto_increment,
    expired    bit    not null,
    revoked    bit    not null,
    token      varchar(255),
    token_type varchar(255),
    member_id  bigint,
    primary key (id)
) engine=InnoDB;
create table token_seq
(
    next_val bigint
) engine=InnoDB;
insert into token_seq
values (1);
alter table member
    add constraint UK_9esvgikrmti1v7ci6v453imdc unique (name);
alter table token
    add constraint UK_pddrhgwxnms2aceeku9s2ewy5 unique (token);
alter table token
    add constraint FK8a0sdl451qcw4ishfaxpdog0p foreign key (member_id) references member (id)