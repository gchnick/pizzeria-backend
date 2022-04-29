-- USER
INSERT INTO Users (full_name, email, password, role) VALUES ('Docs Test', 'test@mail.com', '$2a$10$7TB3GlYYojP3qBKEPoU2Ou1hLsX6/V5iO.V3xMLJCX0rCC0UbrPZm', 'ROLE_ADMIN');

-- CATEGORY
INSERT INTO Categories VALUES (1, 'Pizzas');
INSERT INTO Categories VALUES (2, 'Empanadas');
INSERT INTO Categories VALUES (3, 'Bebidas');
INSERT INTO Categories VALUES (4, 'Postres');

-- PRODUCTS (Pizzas)
INSERT INTO Products (id, name, description, price, category_id) VALUES (1, 'Napolitana', 'Sabrosa pizza con salsa napolitana', 15.65, 1);
INSERT INTO Products VALUES (2, 'Pizza de pollo', 'No apta para vegetarianos pero deliciosa. La salsa barbacoa acompaña exquisitamente al pollo', 17.23, 1);
INSERT INTO Products VALUES (3, 'Pizza 4 quesos', 'Si eres amante del queso no podrás resistirte a una combinación equilibrada de cuatro de sus variedades: mozzarella, queso azul, parmesano, provolone', 18.0, 1);
INSERT INTO Products (id, name, description, price, category_id) VALUES (4, 'Pizza de huevo y panceta', 'Aunque suena a desayuno americano es una de las pizzas más consumidas a nivel mundial', 16.0, 1);
INSERT INTO Products (id, name, description, price, category_id) VALUES (5, 'Margarita', 'Pizza italiana, lleva los colores de su bandera representados por el tomate, la albahaca y la muzzarella', 14.45, 1);

-- PRODUCTS (Empanadas)
INSERT INTO Products (id ,name, description, price, category_id) VALUES (6, 'Empanada de carne', 'Tradicional empanada de carne desmechada', 0.57, 2);

-- PRODUCTS (Bebidas)
INSERT INTO Products (id, name, description, price, category_id) VALUES (7, 'Pepsi', 'Pepsi selfie', 1.2, 3);
INSERT INTO Products (id, name, description, price, category_id) VALUES (8, 'Cerveza Brahma', 'Cerveza suave y frescante', 1.95, 3);
INSERT INTO Products (id, name, description, price, category_id) VALUES (9, 'Agua mineral', 'Nestle Pureza Vital', 0.36, 3);

-- PRODUCTS (Postres)
INSERT INTO Products (id, name, description, price, category_id) VALUES (10, 'Torta tres leches', 'Deliciosa torta para postre', 2.32, 4);