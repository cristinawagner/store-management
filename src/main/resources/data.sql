INSERT INTO categories (name, description, created_at, updated_at)
VALUES ('Books', 'Books Category', current_time, current_time);
INSERT INTO categories (name, description, created_at, updated_at)
VALUES ('Toys', 'Toys Category', current_time, current_time);
INSERT INTO categories (name, description, created_at, updated_at)
VALUES ('Software', 'Software Category', current_time, current_time);
INSERT INTO categories (name, description, created_at, updated_at)
VALUES ('Sport Equipment', 'Sport Equipment Category', current_time, current_time);

INSERT INTO products (name, description, category_id, created_at, updated_at)
VALUES ('Volleyball', 'Volley   ball - normal size 5', 4, current_time, current_time);
INSERT INTO products (name, description, category_id, created_at, updated_at)
VALUES ('Elastic band', 'Fitness elastic band 5 kg', 4, current_time, current_time);

INSERT INTO brands (name, description, created_at, updated_at)
VALUES ('Adidas', 'Adidas sports equipment', current_time, current_time);
INSERT INTO brands (name, description, created_at, updated_at)
VALUES ('Decathlon', 'Decathlon sports equipment', current_time, current_time);

INSERT INTO items (sku, price, quantity, sold, available, brand_id, product_id, created_at, updated_at)
VALUES ('XYZ12345', 100, 50, 20, 30, 2, 1, current_time, current_time);

INSERT INTO items (sku, price, quantity, sold, available, brand_id, product_id, created_at, updated_at)
VALUES ('XYZ12346', 125, 40, 20, 20, 1, 1, current_time, current_time);