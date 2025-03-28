-- src/main/resources/db/migration/V1__Initial_schema.sql

-- Create schema
CREATE SCHEMA IF NOT EXISTS seek;

-- Create clients table
CREATE TABLE seek.clients (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    last_name VARCHAR(255),
    age INT,
    birth_date DATE
);

-- Create users table
CREATE TABLE seek.users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) UNIQUE,
    password VARCHAR(255),
    role VARCHAR(255)
);