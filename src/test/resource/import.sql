-- USER
INSERT INTO Users VALUES (1, 'Jhon Doe', 'jhon.doe@mail.com', 'userpass', 'ROLE_ADMIN');

-- CATEGORY
INSERT INTO Categories VALUES (1, 'Pizzas');
INSERT INTO Categories VALUES (2, 'Empanadas');
INSERT INTO Categories VALUES (3, 'Bebidas');
INSERT INTO Categories VALUES (4, 'Postres');

-- PRODUCTS (Pizzas)
INSERT INTO Products (name, description, price, category_id) VALUES ('Napolitana', 'Excelente pizza con salsa napolitana', 12.6, 1);
INSERT INTO Products (name, description, price, category_id) VALUES ('Peperoni', 'Pizza con panceta', 11.23, 1);

-- PRODUCTS (Empanadas)
INSERT INTO Products (name, description, price, category_id) VALUES ('Empanada de carne', 'Tradicional empanada de carne desmechada', 0.57, 2);

-- PRODUCTS (Bebidas)
INSERT INTO Products (name, description, price, category_id) VALUES ('Pepsi cola', 'Pepsi selfie', 1.23, 3);

-- PRODUCTS (Postres)
INSERT INTO Products (name, description, price, category_id) VALUES ('Torta tres leches', 'Deliciosa torta para postre', 2.32, 4);