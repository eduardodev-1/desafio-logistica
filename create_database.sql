drop schema if exists ecommerce cascade;

create schema ecommerce;

CREATE TABLE IF NOT EXISTS ecommerce.order (
id SERIAL PRIMARY KEY,
old_id BIGINT NOT NULL,
total NUMERIC(10, 2) NOT NULL,
date DATE NOT NULL,
products_json JSONB NOT NULL,
user_json JSONB NOT NULL
);