DROP TABLE IF EXISTS accounts;

CREATE TABLE IF NOT EXISTS accounts
(
    user_id    serial PRIMARY KEY,
    username   varchar(50) UNIQUE NOT NULL,
    password   varchar(128)       NOT NULL,
    email      varchar(128)       NOT NULL,
    balance    decimal   default 0 CHECK ( balance >= 0 ),
    created_on timestamp default current_timestamp,
    last_login timestamp default current_timestamp
);

INSERT INTO accounts (username,
                      password,
                      email,
                      balance)
VALUES ('admin',
        'admin',
        'admin@email.com',
        1000);

SELECT *
FROM accounts;