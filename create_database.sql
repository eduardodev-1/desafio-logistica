drop schema if exists ecommerce cascade;

create schema ecommerce;

-- Criando a tabela 'order'
CREATE TABLE IF NOT EXISTS ecommerce.order (
id SERIAL PRIMARY KEY,
total FLOAT NOT NULL,
date DATE NOT NULL,
products_json JSONB NOT NULL,
user_json JSONB NOT NULL
);