CREATE SCHEMA IF NOT EXISTS PUBLIC;
DROP TABLE IF EXISTS AK_MENU_ORDER;
DROP TABLE IF EXISTS AK_MENU;
DROP TABLE IF EXISTS AK_DISH;
DROP TABLE IF EXISTS AK_NOTIFICATION;
DROP TABLE IF EXISTS AK_TABLE;
DROP TABLE IF EXISTS AK_USER_AUTHORITY;
DROP TABLE IF EXISTS AK_USER;
DROP TABLE IF EXISTS AK_RESTAURANT;
DROP TABLE IF EXISTS AK_USER_RESTAURANT;
DROP TABLE IF EXISTS AK_ORDER;
DROP TABLE IF EXISTS AK_AUTHORITY;

CREATE TABLE AK_DISH
(
    ID          INT AUTO_INCREMENT PRIMARY KEY,
    TITLE       TINYTEXT                            NOT NULL,
    IMG_URL     TINYTEXT,
    DESCRIPTION TEXT,
    CREATED_AT  TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    UPDATED_AT  TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);

CREATE TABLE AK_AUTHORITY
(
    ID   INT AUTO_INCREMENT PRIMARY KEY,
    NAME VARCHAR(255) NOT NULL,
    CONSTRAINT AUTHORITY_NAME_U UNIQUE (NAME)
);

CREATE TABLE AK_USER
(
    ID                  INT AUTO_INCREMENT PRIMARY KEY,
    FIRST_NAME          TINYTEXT                            NOT NULL,
    LAST_NAME           TINYTEXT                            NOT NULL,
    EMAIL               VARCHAR(255)                        NOT NULL,
    PASSWORD_HASH       VARCHAR(255)                        NOT NULL,
    CREATED_AT          TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    UPDATED_AT          TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    ACCOUNT_EXPIRED     BOOLEAN   DEFAULT FALSE,
    ACCOUNT_LOCKED      BOOLEAN   DEFAULT FALSE,
    CREDENTIALS_EXPIRED BOOLEAN   DEFAULT FALSE,
    ENABLED             BOOLEAN   DEFAULT TRUE,

    CONSTRAINT USER_EMAIL_U UNIQUE (EMAIL)
);

CREATE TABLE AK_RESTAURANT
(
    ID          INT AUTO_INCREMENT PRIMARY KEY,
    TITLE       TINYTEXT                            NOT NULL,
    IMG_URL     TINYTEXT,
    DESCRIPTION TEXT,
    CREATED_AT  TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    UPDATED_AT  TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
);

CREATE TABLE AK_USER_RESTAURANT
(
    ID            INT AUTO_INCREMENT PRIMARY KEY,
    USER_ID       INT NOT NULL,
    RESTAURANT_ID INT NOT NULL,
    CONSTRAINT USER_RESTAURANT_USER_ID_RESTAURANT_ID_U UNIQUE (USER_ID, RESTAURANT_ID),
    CONSTRAINT USER_RESTAURANT_USER_ID_FK
        FOREIGN KEY (USER_ID) REFERENCES AK_USER (ID)
            ON
                DELETE CASCADE
            ON
                UPDATE CASCADE,
    CONSTRAINT USER_RESTAURANT_RESTAURANT_ID_FK
        FOREIGN KEY (RESTAURANT_ID) REFERENCES AK_RESTAURANT (ID)
            ON
                DELETE CASCADE
            ON
                UPDATE CASCADE
);

CREATE TABLE AK_USER_AUTHORITY
(
    ID           INT AUTO_INCREMENT PRIMARY KEY,
    USER_ID      INT NOT NULL,
    AUTHORITY_ID INT NOT NULL,
    CONSTRAINT USER_AUTHORITY_USER_ID_AUTHORITY_ID_U UNIQUE (USER_ID, AUTHORITY_ID),
    CONSTRAINT USER_AUTHORITY_USER_ID_FK
        FOREIGN KEY (USER_ID) REFERENCES AK_USER (ID)
            ON
                DELETE CASCADE
            ON
                UPDATE CASCADE,
    CONSTRAINT USER_AUTHORITY_AUTHORITY_ID_FK
        FOREIGN KEY (AUTHORITY_ID) REFERENCES AK_AUTHORITY (ID)
            ON
                DELETE CASCADE
            ON
                UPDATE CASCADE
);

CREATE TABLE AK_TABLE
(
    ID               INT AUTO_INCREMENT PRIMARY KEY,
    TITLE            TINYTEXT                            NOT NULL,
    CREATED_AT       TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    UPDATED_AT       TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    NUMBER_OF_PLACES INT                                 NOT NULL DEFAULT 1,
    RESTAURANT_ID    INT                                 NOT NULL,
    CONSTRAINT TABLE_RESTAURANT_ID_FK
        FOREIGN KEY (RESTAURANT_ID) REFERENCES AK_RESTAURANT (ID)
            ON
                DELETE CASCADE
            ON
                UPDATE CASCADE
);


CREATE TABLE AK_MENU
(
    ID            INT AUTO_INCREMENT PRIMARY KEY,
    CREATED_AT    TIMESTAMP    DEFAULT CURRENT_TIMESTAMP NOT NULL,
    UPDATED_AT    TIMESTAMP    DEFAULT CURRENT_TIMESTAMP NOT NULL,
    STATE         INT NOT NULL DEFAULT 1 COMMENT 'states: 1-available(default), 2-terminated, 3-invisible',
    TYPE          INT NOT NULL DEFAULT 1 COMMENT 'may be used to group menu dishes: 1-default',
    DISH_ID       INT NOT NULL,
    RESTAURANT_ID INT NOT NULL,
    CONSTRAINT MENU_DISH_ID_FK
        FOREIGN KEY (DISH_ID) REFERENCES AK_DISH (ID)
            ON
                DELETE CASCADE
            ON
                UPDATE CASCADE,
    CONSTRAINT MENU_RESTAURANT_ID_FK
        FOREIGN KEY (RESTAURANT_ID) REFERENCES AK_RESTAURANT (ID)
            ON
                DELETE CASCADE
            ON
                UPDATE CASCADE,
    CONSTRAINT MENU_DISH_ID_RESTAURANT_ID_U UNIQUE (DISH_ID, RESTAURANT_ID)
);

CREATE TABLE AK_NOTIFICATION
(
    ID          INT AUTO_INCREMENT PRIMARY KEY,
    TITLE       TINYTEXT                            NOT NULL,
    DESCRIPTION TEXT,
    CREATED_AT  TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    UPDATED_AT  TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
);

CREATE TABLE AK_ORDER
(
    ID              INT AUTO_INCREMENT PRIMARY KEY,
    DESCRIPTION     TEXT,
    CREATED_AT      TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    UPDATED_AT      TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    USER_ID         INT,
    TABLE_ID        INT,
    NOTIFICATION_ID INT,
    CONSTRAINT ORDER_USER_ID_FK
        FOREIGN KEY (USER_ID) REFERENCES AK_USER (ID)
            ON
                DELETE CASCADE
            ON
                UPDATE CASCADE,
    CONSTRAINT ORDER_TABLE_ID_FK
        FOREIGN KEY (TABLE_ID) REFERENCES AK_TABLE (ID)
            ON
                DELETE CASCADE
            ON
                UPDATE CASCADE,
    CONSTRAINT ORDER_NOTIFICATION_ID_FK
        FOREIGN KEY (NOTIFICATION_ID) REFERENCES AK_NOTIFICATION (ID)
            ON
                DELETE CASCADE
            ON
                UPDATE CASCADE
);

CREATE TABLE AK_MENU_ORDER
(
    ID         INT AUTO_INCREMENT PRIMARY KEY,
    CREATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    UPDATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    MENU_ID    INT                                 NOT NULL,
    ORDER_ID   INT                                 NOT NULL,
    CONSTRAINT MENU_ORDER_MENU_ID_FK
        FOREIGN KEY (MENU_ID) REFERENCES AK_MENU (ID)
            ON
                DELETE CASCADE
            ON
                UPDATE CASCADE,
    CONSTRAINT MENU_ORDER_ORDER_ID_FK
        FOREIGN KEY (ORDER_ID) REFERENCES AK_ORDER (ID)
            ON
                DELETE CASCADE
            ON
                UPDATE CASCADE
);

-- auth 2.0

DROP TABLE IF EXISTS oauth_client_details;
DROP TABLE IF EXISTS oauth_client_token;
DROP TABLE IF EXISTS oauth_access_token;
DROP TABLE IF EXISTS oauth_refresh_token;
DROP TABLE IF EXISTS oauth_code;
DROP TABLE IF EXISTS oauth_approvals;



create table oauth_client_details
(
    client_id               varchar(255) not null,
    client_secret           varchar(255) not null,
    web_server_redirect_uri varchar(2048) default null,
    scope                   varchar(255)  default null,
    access_token_validity   int(11)       default null,
    refresh_token_validity  int(11)       default null,
    resource_ids            varchar(1024) default null,
    authorized_grant_types  varchar(1024) default null,
    authorities             varchar(1024) default null,
    additional_information  varchar(4096) default null,
    autoapprove             varchar(255)  default null,
    primary key (client_id)
);

create table oauth_client_token
(
    token_id          VARCHAR(256),
    token             VARBINARY,
    authentication_id VARCHAR(256) PRIMARY KEY,
    user_name         VARCHAR(256),
    client_id         VARCHAR(256)
);
create table oauth_access_token
(
    token_id          VARCHAR(256),
    token             VARBINARY,
    authentication_id VARCHAR(256) PRIMARY KEY,
    user_name         VARCHAR(256),
    client_id         VARCHAR(256),
    authentication    VARBINARY,
    refresh_token     VARCHAR(256)
);

create table oauth_refresh_token
(
    token_id       VARCHAR(256),
    token          VARBINARY,
    authentication VARBINARY
);

create table oauth_code
(
    code           VARCHAR(256),
    authentication VARBINARY
);

create table oauth_approvals
(
    userId         VARCHAR(256),
    clientId       VARCHAR(256),
    scope          VARCHAR(256),
    status         VARCHAR(10),
    expiresAt      TIMESTAMP,
    lastModifiedAt TIMESTAMP
);

-- create table if not exists permission
-- (
--     id   int(11) not null auto_increment,
--     name varchar(512) default null,
--     primary key (id),
--     CONSTRAINT permission_name_u UNIQUE (name)
--
-- );
--
-- create table if not exists role
-- (
--     id   int(11) not null auto_increment,
--     name varchar(255) default null,
--     primary key (id),
--     CONSTRAINT role_name_u UNIQUE (name)
-- );


-- create table if not exists user
-- (
--     id                    int(11)       not null auto_increment,
--     username              varchar(100)  not null,
--     password              varchar(1024) not null,
--     email                 varchar(1024) not null,
--     enabled               tinyint(4)    not null,
--     accountNonExpired     tinyint(4)    not null,
--     credentialsNonExpired tinyint(4)    not null,
--     accountNonLocked      tinyint(4)    not null,
--     primary key (id),
--     CONSTRAINT username_u UNIQUE (username)
-- );
--
--
-- create table if not exists permission_role
-- (
--     permission_id int(11) default null,
--     role_id       int(11) default null,
--     constraint permission_role_ibfk_1 foreign key (permission_id) references permission (id),
--     constraint permission_role_ibfk_2 foreign key (role_id) references role (id)
-- );
--
--
--
-- create table if not exists role_user
-- (
--     role_id int(11) default null,
--     user_id int(11) default null,
--     constraint role_user_ibfk_1 foreign key (role_id) references role (id),
--     constraint role_user_ibfk_2 foreign key (user_id) references user (id)
-- );


