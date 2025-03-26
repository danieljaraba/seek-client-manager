-- src/main/resources/db/migration/V2__Add_clients.sql

-- Insert example clients
INSERT INTO seek.clients (id, name, last_name, age, birth_date) VALUES
    (UUID(), 'John', 'Doe', 30, '1993-01-15'),
    (UUID(), 'Jane', 'Smith', 25, '1998-05-22'),
    (UUID(), 'Alice', 'Johnson', 28, '1995-03-10'),
    (UUID(), 'Bob', 'Brown', 35, '1988-07-30');