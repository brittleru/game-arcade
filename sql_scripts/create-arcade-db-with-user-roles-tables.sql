DROP DATABASE IF EXISTS `arcade_host`;

CREATE DATABASE IF NOT EXISTS `arcade_host`;
USE `arcade_host`;

/*
 *
 * Generating table structure for `user`
 *
 */
DROP TABLE IF EXISTS `user`;

CREATE TABLE `user`
(
    `id`            int(11)     NOT NULL AUTO_INCREMENT,
    `username`      varchar(50) NOT NULL UNIQUE,
    `password`      char(80)    NOT NULL,
    `first_name`    varchar(50) NOT NULL,
    `last_name`     varchar(50) NOT NULL,
    `email`         varchar(50) NOT NULL UNIQUE,
    `user_image_id` INT(11),
    `created_at`    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `updated_at`    DATETIME  DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;

/*
*
* Populating table with some data for users, passwords are encrypted with BCrypt
* Default password is: Arcade123!
*
*/
INSERT INTO `user` (username, password, first_name, last_name, email)
VALUES ('brittle', '$2a$12$Vel7WbGZgK/PvWfXFLKWYuPM1qFMk/qkGJTSJY0yQiizi4Sww.K/C', 'Sebastian', 'Mocanu',
        'sebastian@gmail.com'),
       ('medi', '$2a$12$Vel7WbGZgK/PvWfXFLKWYuPM1qFMk/qkGJTSJY0yQiizi4Sww.K/C', 'Medi', 'Mocanu',
        'medi@gmail.com'),
       ('jane', '$2a$12$Vel7WbGZgK/PvWfXFLKWYuPM1qFMk/qkGJTSJY0yQiizi4Sww.K/C', 'Jane', 'Doe', 'jane.doe@gmail.com');

/*
*
* Generating table structure for `user_image`;
*
*/
DROP TABLE IF EXISTS `user_image`;

CREATE TABLE `user_image`
(
    `id`         int(11)      NOT NULL AUTO_INCREMENT,
    `file_name`  varchar(255) NOT NULL,
    `type`       varchar(100) NOT NULL,
    `image_data` LONGBLOB     NOT NULL,
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME  DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;


ALTER TABLE `user`
    ADD FOREIGN KEY (`user_image_id`) REFERENCES user_image (`id`) ON DELETE CASCADE;

/*
 *
 * Generating table structure for `role`
 *
 */
DROP TABLE IF EXISTS `role`;

CREATE TABLE `role`
(
    `id`   int(11) NOT NULL AUTO_INCREMENT,
    `name` varchar(50) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;


/*
 *
 * Populate new table with role data
 *
 */
INSERT INTO `role` (name)
VALUES ('ROLE_NORMAL'),
       ('ROLE_ADMIN'),
       ('ROLE_SUPERUSER');


/*
*
* Generating table structure for `users_roles`;
*
*/
DROP TABLE IF EXISTS `users_roles`;

CREATE TABLE `users_roles`
(
    `user_id` int(11) NOT NULL,
    `role_id` int(11) NOT NULL,

    PRIMARY KEY (`user_id`, `role_id`),
    KEY `FK_ROLE_idx` (`role_id`),

    CONSTRAINT `FK_USER_05` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION

) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;

SET FOREIGN_KEY_CHECKS = 1;

INSERT INTO `users_roles` (user_id, role_id)
VALUES (1, 1),
       (1, 2),
       (1, 3),
       (2, 1),
       (2, 2),
       (3, 1);

/*
*
* Generating table structure for `high_score`;
*
*/
DROP TABLE IF EXISTS `high_score`;
CREATE TABLE `high_score`
(
    `id`         int(11)      NOT NULL AUTO_INCREMENT,
    `game`       varchar(255) NOT NULL,
    `score`      int          NOT NULL,
    `username`   varchar(50)  NOT NULL,
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME  DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    foreign key (`username`) references user (`username`),
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;