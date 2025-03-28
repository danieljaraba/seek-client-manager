-- src/main/resources/db/migration/V2__Add_clients.sql

-- Insert example clients
INSERT INTO seek.clients (id, name, last_name, age, birth_date) VALUES
    (1, 'John', 'Doe', 30, '1993-01-15'),
    (2, 'Jane', 'Smith', 25, '1998-05-22'),
    (3, 'Alice', 'Johnson', 28, '1995-03-10'),
    (4, 'Bob', 'Brown', 35, '1988-07-30');