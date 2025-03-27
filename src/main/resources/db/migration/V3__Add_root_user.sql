-- src/main/resources/db/migration/V3__Add_root_user.sql

-- Add root user (password: root)
INSERT INTO seek.users (email, password, role) VALUE
    ('root@gmail.com', '$2a$12$oAws/SxzqEYrvBfHVT58lunV93cESICSsbl3gJ5a8GBW3oOqY7oJq', 'ROLE_ADMIN')