-- Insert permissions
INSERT INTO permissions (permission_name) VALUES ('READ');
INSERT INTO permissions (permission_name) VALUES ('WRITE');
INSERT INTO permissions (permission_name) VALUES ('DELETE');
INSERT INTO permissions (permission_name) VALUES ('UPDATE');

-- Insert roles
INSERT INTO roles (name) VALUES ('ADMIN');
INSERT INTO roles (name) VALUES ('EMPLOYEE');
INSERT INTO roles (name) VALUES ('CUSTOMER');

-- Link ADMIN role to all permissions
INSERT INTO role_permissions (role_id, permission_id)
SELECT r.id, p.id FROM roles r CROSS JOIN permissions p WHERE r.name = 'ADMIN';

-- Set Permissions for role Employee and Customer
INSERT INTO role_permissions (role_id, permission_id) VALUES (2, 1);
INSERT INTO role_permissions (role_id, permission_id) VALUES (2, 2);
INSERT INTO role_permissions (role_id, permission_id) VALUES (2, 4);
INSERT INTO role_permissions (role_id, permission_id) VALUES (3, 2);
INSERT INTO role_permissions (role_id, permission_id) VALUES (3, 4);


-- Create default admin user
INSERT INTO users (first_name, last_name, email, password, phone_number, role_id, deleted)
VALUES (
    'Admin',
    'Admin',
    'admin@admin.com',
    '$2a$10$MNXGGrRmkQBJLU/SYUqdy.G0ZU0wQYniWhY63a72CwsUKXXY.WEDC',
    '0000000000',
    1,
    false
);

INSERT INTO product (name, category, description, price, quantity)
VALUES (
'Laptop',
'Electronics',
'Asus laptop 16GB RAM',
4000,
10
);

INSERT INTO product (name, category, description, price, quantity)
VALUES (
'Shirt',
'Clothes',
'100% Cotton',
200,
15
);