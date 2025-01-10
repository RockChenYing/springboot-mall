INSERT INTO product (product_name, category, image_url, price, stock, description, created_date, last_modified_date)
VALUES ('蘋果 (澳洲)', 'FOOD', 'https://cdn.pixabay.com/photo/2016/11/30/15/00/apples-1872997_1280.jpg', 30, 10, '這是來自澳洲的蘋果！', '2022-03-19 17:00:00', '2022-03-22 18:00:00');

INSERT INTO product (product_name, category, image_url, price, stock, description, created_date, last_modified_date)
VALUES ('蘋果 (日本北海道)', 'FOOD', 'https://cdn.pixabay.com/photo/2017/06/13/42/apple-2788662_1280.jpg', 300, 5, '這是來自日本北海道的蘋果！', '2022-03-19 18:30:00', '2022-03-19 18:30:00');

INSERT INTO product (product_name, category, image_url, price, stock, description, created_date, last_modified_date)
VALUES ('好吃又甜的蘋果椪子', 'FOOD', 'https://cdn.pixabay.com/photo/2021/07/03/04/17/orange-6508617_1280.jpg', 10, 50, NULL, '2022-03-20 10:50:00', '2022-03-24 14:50:00');

INSERT INTO product (product_name, category, image_url, price, stock, description, created_date, last_modified_date)
VALUES ('Toyota', 'CAR', 'https://cdn.pixabay.com/photo/2014/05/18/19/13/toyota-347288_1280.jpg', 100000, 5, NULL, '2022-03-20 09:20:00', '2022-03-20 09:20:00');

INSERT INTO product (product_name, category, image_url, price, stock, description, created_date, last_modified_date)
VALUES ('BMW', 'CAR', 'https://cdn.pixabay.com/photo/2018/02/21/03/15/bmw-m4-3169357_1280.jpg', 500000, 3, '溫馨提醒：運動模式可切換至 DOHC 雙渦輪 + 16氣門', '2022-03-20 12:30:00', '2022-03-20 12:30:00');

INSERT INTO product (product_name, category, image_url, price, stock, description, created_date, last_modified_date)
VALUES ('Benz', 'CAR', 'https://cdn.pixabay.com/photo/2017/03/27/14/44/car-2179272_1280.jpg', 600000, 2, NULL, '2022-03-21 10:50:00', '2022-03-21 10:50:00');

INSERT INTO product (product_name, category, image_url, price, stock, description, created_date, last_modified_date)
VALUES ('Tesla', 'CAR', 'https://cdn.pixabay.com/photo/2021/11/15/16/49/tesla-5919764_1280.jpg', 450000, 5, '世界最酷的無污染汽車！', '2022-03-21 23:30:00', '2022-03-21 23:30:00');

-- user
INSERT INTO user (email, password, created_date, last_modified_date) VALUES ('user1@gmail.com', '202cb962ac59075b964b07152d234b70', '2022-06-30 10:30:00', '2022-06-30 10:30:00');
INSERT INTO user (email, password, created_date, last_modified_date) VALUES ('user2@gmail.com', '202cb962ac59075b964b07152d234b70', '2022-06-30 10:40:00', '2022-06-30 10:40:00');

-- order, order_item
INSERT INTO `order` (user_id, total_amount, created_date, last_modified_date) VALUES (1, 500690, '2022-06-30 11:10:00', '2022-06-30 11:10:00');
INSERT INTO order_item (order_id, product_id, quantity, amount) VALUES (1, 1, 3, 90);
INSERT INTO order_item (order_id, product_id, quantity, amount) VALUES (1, 2, 2, 600);
INSERT INTO order_item (order_id, product_id, quantity, amount) VALUES (1, 5, 1, 500000);

INSERT INTO `order` (user_id, total_amount, created_date, last_modified_date) VALUES (1, 100000, '2022-06-30 12:03:00', '2022-06-30 12:03:00');
INSERT INTO order_item (order_id, product_id, quantity, amount) VALUES (2, 4, 1, 100000);
