-- src/main/resources/db/migration/V1__Initial_schema.sql

-- Create schema
CREATE SCHEMA IF NOT EXISTS seek;

-- Create clients table
CREATE TABLE seek.clients (
    id CHAR(36) PRIMARY KEY,
    name VARCHAR(255),
    lastName VARCHAR(255),
    age INT,
    birthDate DATE
);

-- Create users table
CREATE TABLE seek.users (
    id CHAR(36) PRIMARY KEY,
    email VARCHAR(255) UNIQUE,
    password VARCHAR(255)
);