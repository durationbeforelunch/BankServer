DROP TABLE IF EXISTS accounts;

CREATE TABLE IF NOT EXISTS accounts
(
    user_id    serial PRIMARY KEY,
    username   varchar(50) UNIQUE NOT NULL,
    password   varchar            NOT NULL,
    email      varchar(128)       NOT NULL,
    balance    decimal     default 0 CHECK ( balance >= 0 ),
    role       varchar(20) default 'USER',
    status     varchar(20) default 'ACTIVE',
    created_on timestamp   default current_timestamp,
    last_login timestamp   default current_timestamp
);

INSERT INTO accounts (username,
                      password,
                      email,
                      balance,
                      role)
VALUES ('admin',
        '$2a$12$HLDUE6cDSuMTqaj8nzsrVeWl8yK85pgIElejPeI0g30tB1K.///Sm',
        'admin@mail.com',
        500000,
        'ADMIN');

SELECT *
FROM accounts;