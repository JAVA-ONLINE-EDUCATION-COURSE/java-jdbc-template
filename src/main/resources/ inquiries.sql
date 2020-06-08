/* 1. Список ставок данного пользователя */

SELECT bids.bid_id, bids.bid_date, bids.bid_value, users.full_name
FROM bids
INNER JOIN users ON bids.users_user_id = users.user_idWHERE users.full_name = '?';

/* 2. Список лотов данного пользователя*/
SELECT items.title, items.description, items.start_date, items.stop_date, users.full_name
FROM items
INNER JOIN users ON items.users_user_id = users.user_id
WHERE users.full_name  = 'Albert';

/* 3. Поиск лотов по подстроке в названии*/
SELECT * FROM items
WHERE title LIKE '%ина';

/* 4. Поиск лотов по подстроке в описании */
SELECT * FROM items
WHERE description LIKE '%8';

/* 5. Средняя цена лотов каждого пользователя*/
SELECT AVG(items.start_price), users.full_name
FROM items INNER JOIN users ON items.users_user_id = users.user_id
GROUP BY full_name;

/* 6. Максимальный размер имеющихся ставок на каждый лот*/
SELECT MAX(bids.bid_value), items.title 
FROM bids INNER JOIN items ON bids.items_item_id = items.item_id
GROUP BY items.item_id;

/* 7. Список действующих лотов данного пользователя*/
SELECT items.title, items.by_it_now, users.full_name 
FROM items INNER JOIN users ON items.users_user_id = users.user_id
WHERE items.by_it_now = true
GROUP BY users.full_name;

/* 8. Добавить нового пользователя*/
INSERT INTO users (full_name, billing_address, login, password) VALUES
('new Person', 'Moskow', 'traider', '123456');

/* 9. Добавить новый лот*/
INSERT INTO items (title, description, start_price, bid_increment, start_date, stop_date, by_it_now, users_user_id) VALUES
('new lot', 'Лот 11', 123.00, 14.00, '2020-05-21', '2020-06-10', TRUE, null);

/* 10. Удалить ставки данного пользователя*/
DELETE FROM bids 
WHERE bids.users_user_id = (SELECT users.user_id FROM users WHERE users.full_name = 'Albert');

/* 11. Удалить лоты данного пользователя*/
DELETE FROM items
WHERE items.users_user_id = (SELECT users.user_id FROM users WHERE users.full_name = 'Albert');

/* 12. Увеличить стартовые цены товаров данного пользователя вдвое*/
SET SQL_SAFE_UPDATES=0;
UPDATE items SET
	items.start_price = items.start_price * 2
WHERE items.users_user_id IN (SELECT users.user_id FROM users WHERE users.full_name = 'Albert') ;