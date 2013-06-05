# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table user (
  id                        bigint not null,
  username                  varchar(255),
  password                  varchar(255),
  email                     varchar(255),
  verify_email              boolean,
  mobile                    varchar(255),
  verify_mobile             boolean,
  id_num                    varchar(255),
  real_name                 varchar(255),
  status                    boolean,
  constraint pk_user primary key (id))
;

create table verifycode (
  id                        bigint not null,
  username                  varchar(255),
  active_code               varchar(255),
  verify_email_code         varchar(255),
  verify_mobile_code        varchar(255),
  constraint pk_verifycode primary key (id))
;

create sequence user_seq;

create sequence verifycode_seq;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists user;

drop table if exists verifycode;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists user_seq;

drop sequence if exists verifycode_seq;

