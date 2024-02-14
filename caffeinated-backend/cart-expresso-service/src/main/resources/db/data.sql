INSERT INTO carts (total_price, created_at, user_id)
VALUES (778, CURRENT_TIMESTAMP, 1);

-- Get the ID of the last inserted cart
SET @lastCartId = LAST_INSERT_ID();

-- Insert cart items for the user_id 1 with the specified product details
INSERT INTO cart_items (cart_id, product_id, product_name, product_quantity, unit_price, total_price)
VALUES
  (@lastCartId, 1, 'Royal Velvet Delight', 1, 349, 349),
  (@lastCartId, 2, 'Golden Dawn Elixir', 1, 429, 429);