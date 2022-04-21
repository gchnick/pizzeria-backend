-- USER
INSERT INTO Users (full_name, email, password, role) VALUES ('Jhon Doe', 'jhon.doe@mail.com', '$2a$10$7TB3GlYYojP3qBKEPoU2Ou1hLsX6/V5iO.V3xMLJCX0rCC0UbrPZm', 'ROLE_ADMIN');

-- CATEGORY
INSERT INTO Categories VALUES (1, 'Pizzas');
INSERT INTO Categories VALUES (2, 'Empanadas');
INSERT INTO Categories VALUES (3, 'Bebidas');
INSERT INTO Categories VALUES (4, 'Postres');

-- PRODUCTS (Pizzas)
INSERT INTO Products (id, name, description, price, category_id) VALUES (1, 'Napolitana', 'Excelente pizza con salsa napolitana', 12.6, 1);
INSERT INTO Products (id, name, description, price, category_id) VALUES (2, 'Peperoni', 'Pizza con panceta', 11.23, 1);
INSERT INTO Products (id, name, description, price, category_id) VALUES (3, 'Pizza de jamon', 'Con jamon de espalda', 10.50, 1);

-- PRODUCTS (Empanadas)
INSERT INTO Products (id ,name, description, price, category_id) VALUES (4, 'Empanada de carne', 'Tradicional empanada de carne desmechada', 0.57, 2);

-- PRODUCTS (Bebidas)
INSERT INTO Products (id, name, description, price, category_id) VALUES (5, 'Pepsi cola', 'Pepsi selfie', 1.23, 3);

-- PRODUCTS (Postres)
INSERT INTO Products (id, name, description, price, category_id) VALUES (6, 'Torta tres leches', 'Deliciosa torta para postre', 2.32, 4);