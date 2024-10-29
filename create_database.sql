drop schema if exists ecommerce cascade;

create schema ecommerce;

CREATE TABLE IF NOT EXISTS ecommerce.user
(
    id   INTEGER PRIMARY KEY,
    name VARCHAR(45) NOT NULL
);

CREATE TABLE IF NOT EXISTS ecommerce.order
(
    id            INTEGER PRIMARY KEY,
    total         NUMERIC(10, 2) NOT NULL,
    date          DATE           NOT NULL,
    products_json JSONB          NOT NULL,
    user_id       INTEGER REFERENCES ecommerce.user (id)
);