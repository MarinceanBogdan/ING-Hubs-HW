-- Insert permissions
INSERT INTO permissions (permission_name) VALUES ('READ');
INSERT INTO permissions (permission_name) VALUES ('WRITE');
INSERT INTO permissions (permission_name) VALUES ('DELETE');

-- Insert roles
INSERT INTO roles (name) VALUES ('ADMIN');
INSERT INTO roles (name) VALUES ('EMPLOYEE');
INSERT INTO roles (name) VALUES ('CUSTOMER');

-- Link ADMIN role to all permissions
INSERT INTO role_permissions (role_id, permission_id)
SELECT r.id, p.id FROM roles r CROSS JOIN permissions p WHERE r.name = 'ADMIN';

-- Create default admin user (password stored in plain text for now)
INSERT INTO users (first_name, last_name, email, password, phone_number, role_id, deleted)
VALUES (
    'Admin',
    'Admin',
    'admin@example.com',
    'admin123', -- ⚠️ Replace with hashed password if using Spring Security
    '0000000000',
    1,
    false
);