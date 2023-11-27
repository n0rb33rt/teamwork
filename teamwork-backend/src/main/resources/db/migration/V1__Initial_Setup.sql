CREATE TABLE employees
(
    id          BIGSERIAL PRIMARY KEY,
    first_name  VARCHAR(30) NOT NULL ,
    last_name   VARCHAR(30) NOT NULL ,
    email       VARCHAR(150) NOT NULL ,
    card_number VARCHAR(16) NOT NULL ,
    working     BOOLEAN NOT NULL,

    CONSTRAINT check_email CHECK(email ~ '^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$')
);

CREATE TABLE transactions
(
    id         BIGSERIAL PRIMARY KEY,
    order_type VARCHAR(23) NOT NULL ,
    date       DATE NOT NULL ,
    paid       BOOLEAN NOT NULL
);

CREATE TABLE transaction_employees
(
    transaction_id BIGINT ,
    employee_id    BIGINT ,
    PRIMARY KEY (transaction_id, employee_id),
    FOREIGN KEY (transaction_id) REFERENCES transactions (id),
    FOREIGN KEY (employee_id) REFERENCES employees (id)
);


