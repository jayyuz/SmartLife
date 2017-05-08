
create database bolg;

CREATE TABLE `user` (
`id`  int NOT NULL AUTO_INCREMENT ,
`nickname`  varchar(45) CHARACTER SET utf8 NOT NULL ,
`password`  varchar(45) CHARACTER SET utf8 NOT NULL ,
`first_name`  varchar(45) CHARACTER SET utf8 NULL ,
`last_name`  varchar(45) CHARACTER SET utf8 NULL ,
PRIMARY KEY (`id`)
)
;

CREATE TABLE `blog` (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`title`  varchar(100) CHARACTER SET utf8 NOT NULL ,
`content`  varchar(255) CHARACTER SET utf8 NOT NULL ,
`user_id`  int(11) NOT NULL ,
`pub_date`  date NOT NULL ,
PRIMARY KEY (`id`),
CONSTRAINT `user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
)
;
