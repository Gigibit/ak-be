DROP TABLE IF EXISTS AK_MENU;
DROP TABLE IF EXISTS AK_DISH;
DROP TABLE IF EXISTS AK_TABLE;
DROP TABLE IF EXISTS AK_USER;
DROP TABLE IF EXISTS AK_RESTAURANT;
DROP TABLE IF EXISTS AK_ORDER;
CREATE TABLE AK_DISH
(
    ID          INT AUTO_INCREMENT PRIMARY KEY,
    TITLE       TINYTEXT                            NOT NULL,
    IMG_URL     TINYTEXT,
    DESCRIPTION TEXT,
    CREATED_AT  TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    UPDATED_AT  TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);

CREATE TABLE AK_USER
(
    ID         INT AUTO_INCREMENT PRIMARY KEY,
    FIRST_NAME TINYTEXT                            NOT NULL,
    LAST_NAME  TINYTEXT                            NOT NULL,
    EMAIL      VARCHAR(255)                        NOT NULL,
    CREATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    UPDATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
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


CREATE TABLE AK_ORDER
(
    ID         INT AUTO_INCREMENT PRIMARY KEY,
    CREATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    UPDATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    MENU_ID    INT                                 NOT NULL,
    USER_ID    INT                                 NOT NULL,
    TABLE_ID   INT,
    CONSTRAINT ORDER_MENU_ID_FK
        FOREIGN KEY (MENU_ID) REFERENCES AK_MENU (ID)
            ON
                DELETE CASCADE
            ON
                UPDATE CASCADE,
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
                UPDATE CASCADE
);



