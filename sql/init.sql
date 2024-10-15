CREATE DATABASE IF NOT EXISTS 'bank_management';
USE 'bank_management';
CREATE TABLE IF NOT EXISTS 'users'
(
    'id'         BIGINT(11)                                 NOT NULL AUTO_INCREMENT,
    'username'   VARCHAR(50)                                NOT NULL,
    'password'   VARCHAR(255)                               NOT NULL,
    'first_name' VARCHAR(50)                                NOT NULL,
    'last_name'  VARCHAR(50)                                NOT NULL,
    'role'       ENUM ('CLIENT', 'BANKER', 'ADMINISTRATOR') NOT NULL,
    PRIMARY KEY ('id'),
    UNIQUE KEY 'username' ('username')
);
CREATE TABLE IF NOT EXISTS 'accounts'
(
    'id'        BIGINT(11)     NOT NULL AUTO_INCREMENT,
    'rib'       CHAR(36)       NOT NULL DEFAULT UUID(),
    'balance'   DECIMAL(19, 2) NOT NULL,
    'client_id' BIGINT(11)     NOT NULL,
    PRIMARY KEY ('id'),
    UNIQUE KEY 'account_number' ('rib'),
    CONSTRAINT 'accounts_user_id_fk' FOREIGN KEY ('client_id') REFERENCES 'users' ('id') ON DELETE CASCADE
);
INSERT INTO 'users' ('username', 'password', 'first_name', 'last_name', 'role')
VALUES ('admin', 'admin', 'admin', 'admin', 'ADMINISTRATOR'),
       ('banker', 'banker', 'banker', 'banker', 'BANKER'),
       ('client', 'client', 'client', 'client', 'CLIENT');
