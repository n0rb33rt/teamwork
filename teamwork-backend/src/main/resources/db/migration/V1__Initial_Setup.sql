CREATE TABLE employees
(
    id          BIGSERIAL PRIMARY KEY,
    first_name  VARCHAR(30),
    last_name   VARCHAR(30),
    email       VARCHAR(150),
    card_number VARCHAR(16)
);

CREATE TABLE transactions
(
    id         BIGSERIAL PRIMARY KEY,
    order_type VARCHAR(23),
    date       TIMESTAMP,
    paid       BOOLEAN
);

CREATE TABLE transaction_employees
(
    transaction_id BIGINT,
    employee_id    BIGINT,
    PRIMARY KEY (transaction_id, employee_id),
    FOREIGN KEY (transaction_id) REFERENCES transactions (id),
    FOREIGN KEY (employee_id) REFERENCES employees (id)
);


