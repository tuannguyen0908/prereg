drop table if exists registration;
create table registration (
        id bigint not null auto_increment,
        mobile_no varchar(255) not null,
        reference_num bigint not null,
        referred_by varchar(255),
        registered_time datetime not null,
        primary key (id)
    );
alter table registration add constraint uk_mobile_no unique (mobile_no);