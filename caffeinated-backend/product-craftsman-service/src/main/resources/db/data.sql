-- Insert dummy data into the categories table
INSERT INTO categories (name, created_at)
VALUES
  ('coffee', CURRENT_TIMESTAMP),
  ('tea', CURRENT_TIMESTAMP),
  ('chocolate', CURRENT_TIMESTAMP),
  ('cookies', CURRENT_TIMESTAMP);

-- Insert dummy data into the products table for coffee category
INSERT INTO products (name, description, price, stock_quantity, available, category_id, created_at, image_path)
VALUES
  ('Royal Velvet Delight', 'Indulge in the luxurious blend of smooth Arabica beans, crowned with a velvety crema. Fit for royalty!', 349, 100, 1, 1, CURRENT_TIMESTAMP, 'path/to/image1.jpg'),
  ('Golden Dawn Elixir', 'Awaken your senses with this golden-hued coffee, infused with hints of caramel and a touch of magic.', 429, 120, 1, 1, CURRENT_TIMESTAMP, 'path/to/image2.jpg'),
  ('Silken Symphony Mocha', 'A harmonious dance of rich chocolate and espresso, elegantly crowned with a silken swirl of whipped cream.', 379, 80, 1, 1, CURRENT_TIMESTAMP, 'path/to/image3.jpg'),
  ('Moonlit Maple Latte', 'Dive into the velvety embrace of a maple-infused latte, capturing the essence of a serene moonlit night.', 419, 90, 1, 1, CURRENT_TIMESTAMP, 'path/to/image4.jpg'),
  ('Opulent Orchid Espresso', 'An exotic espresso adorned with the essence of orchid, creating a truly opulent and fragrant experience.', 459, 110, 1, 1, CURRENT_TIMESTAMP, 'path/to/image5.jpg');

-- Insert dummy data into the products table for tea category
INSERT INTO products (name, description, price, stock_quantity, available, category_id, created_at, image_path)
VALUES
  ('Enchanted Elderflower Elixir', 'A mystical blend of elderflower, chamomile, and hints of lavender. Experience the magic in every sip.', 999, 60, 1, 2, CURRENT_TIMESTAMP, 'path/to/image6.jpg'),
  ('Golden Dragon Oolong', 'Unleash the power of this rare oolong tea, harvested from high mountain gardens. Smooth and enchanting with a hint of orchid fragrance.', 1599, 80, 1, 2, CURRENT_TIMESTAMP, 'path/to/image7.jpg'),
  ('Moonlit Jasmine Pearl', 'Hand-rolled jasmine pearls unfurl in your cup, releasing a delicate floral aroma. Transport yourself to a moonlit garden with each sip.', 1199, 100, 1, 2, CURRENT_TIMESTAMP, 'path/to/image8.jpg'),
  ('Cherry Blossom Symphony', 'A harmonious blend of green tea infused with the essence of cherry blossoms. Celebrate the fleeting beauty of spring all year round.', 1299, 75, 1, 2, CURRENT_TIMESTAMP, 'path/to/image9.jpg'),
  ('Ruby Velvet Chai', 'An exotic blend of black tea, spices, and a touch of ruby cocoa. Indulge in the velvety richness of this decadent chai experience.', 1899, 90, 1, 2, CURRENT_TIMESTAMP, 'path/to/image10.jpg');

-- Insert dummy data into the products table for chocolate category
INSERT INTO products (name, description, price, stock_quantity, available, category_id, created_at, image_path)
VALUES
  ('Dark Chocolate Delight', 'Indulge in the rich and intense flavor of our premium dark chocolate. A true delight for chocolate enthusiasts.', 299, 50, 1, 3, CURRENT_TIMESTAMP, 'path/to/image11.jpg'),
  ('Caramel Crunch Bliss', 'Experience the perfect combination of smooth caramel and crunchy bits, creating a blissful chocolate indulgence.', 349, 60, 1, 3, CURRENT_TIMESTAMP, 'path/to/image12.jpg'),
  ('Minty Fresh Chocolate', 'A refreshing twist on classic chocolate, infused with the cool essence of mint. Perfect for a delightful treat.', 379, 40, 1, 3, CURRENT_TIMESTAMP, 'path/to/image13.jpg'),
  ('Hazelnut Symphony', 'Savor the symphony of chocolate and hazelnuts in every bite. A truly harmonious chocolate experience.', 429, 45, 1, 3, CURRENT_TIMESTAMP, 'path/to/image14.jpg'),
  ('Raspberry Dream Delight', 'Indulge in the dreamy combination of rich chocolate and luscious raspberry flavor. A chocolate lover\'s dream come true.', 459, 55, 1, 3, CURRENT_TIMESTAMP, 'path/to/image15.jpg');

-- Insert dummy data into the products table for cookies category
INSERT INTO products (name, description, price, stock_quantity, available, category_id, created_at, image_path)
VALUES
  ('Classic Chocolate Chip Cookies', 'Enjoy the timeless delight of our classic chocolate chip cookies, baked to perfection for a heavenly taste.', 149, 80, 1, 4, CURRENT_TIMESTAMP, 'path/to/image16.jpg'),
  ('Butter Pecan Delight Cookies', 'Indulge in the rich buttery goodness and crunchy pecans of these delightful cookies. A true treat for your taste buds.', 189, 65, 1, 4, CURRENT_TIMESTAMP, 'path/to/image17.jpg'),
  ('Oatmeal Raisin Bliss Cookies', 'Experience the perfect blend of chewy oatmeal and sweet raisins, creating a blissful cookie delight.', 169, 70, 1, 4, CURRENT_TIMESTAMP, 'path/to/image18.jpg'),
  ('Double Chocolate Fudge Cookies', 'Double the chocolate, double the fudge! Dive into the decadent world of our double chocolate fudge cookies.', 209, 55, 1, 4, CURRENT_TIMESTAMP, 'path/to/image19.jpg'),
  ('Snickerdoodle Symphony Cookies', 'Savor the symphony of cinnamon and sugar in our snickerdoodle cookies. An irresistible treat for any occasion.', 179, 75, 1, 4, CURRENT_TIMESTAMP, 'path/to/image20.jpg');
