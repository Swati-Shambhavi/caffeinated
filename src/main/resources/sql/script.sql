use caffeinated;
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL
);
//Not using authorities table in our api, rather we are making use of hierarchical roles
//in order. So, ADMIN > USER, no need to give 2 roles to ADMIN (USER and ADMIN)
CREATE TABLE `authorities` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `authorities_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
);
 INSERT INTO `authorities` (`user_id`, `name`)
  VALUES (1, 'ROLE_USER');

 INSERT INTO `authorities` (`user_id`, `name`)
  VALUES (1, 'ROLE_ADMIN');
  
  CREATE TABLE IF NOT EXISTS `address` (
  `address_id` int NOT NULL AUTO_INCREMENT,
  `address1` varchar(200) NOT NULL,
  `address2` varchar(200) DEFAULT NULL,
  `city` varchar(50) NOT NULL,
  `state` varchar(50) NOT NULL,
  `pin_code` int NOT NULL,
  `created_at` TIMESTAMP NOT NULL,
  `updated_at` TIMESTAMP DEFAULT NULL,
   PRIMARY KEY (`address_id`)
);

ALTER TABLE users
  ADD COLUMN `name` varchar(100) NOT NULL,
  ADD COLUMN `mobile_number` varchar(20) NOT NULL,
  ADD COLUMN `address_id` int NULL,
  ADD COLUMN `created_at` TIMESTAMP NOT NULL,
  ADD COLUMN `updated_at` TIMESTAMP DEFAULT NULL;
  
  ALTER TABLE users
  ADD FOREIGN KEY (`address_id`) REFERENCES address(`address_id`);
  
CREATE TABLE categories (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
      `created_at` TIMESTAMP NOT NULL,
    `updated_at` TIMESTAMP DEFAULT NULL
);

CREATE TABLE products (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    price DOUBLE NOT NULL,
    stock_quantity INT NOT NULL,
    available BOOLEAN NOT NULL,
    category_id INT NOT NULL,
    discount_percentage DOUBLE,
    discount_start_date DATE,
    discount_end_date DATE,
    `created_at` TIMESTAMP NOT NULL,
    `updated_at` TIMESTAMP DEFAULT NULL,
    FOREIGN KEY (category_id) REFERENCES categories(id)
);

ALTER TABLE products
ADD COLUMN image_path VARCHAR(255) NULL; 

CREATE TABLE `carts` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `total_price` DECIMAL(10, 2) NOT NULL DEFAULT 0,
  `created_at` TIMESTAMP NOT NULL,
  `updated_at` TIMESTAMP NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  `user_id` INT NOT NULL, -- Added user_id column
  CONSTRAINT `fk_user_cart`
    FOREIGN KEY (`user_id`)
    REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `cart_items` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `cart_id` INT NOT NULL,
  `product_id` INT NOT NULL,
  `quantity` INT NOT NULL,
  `unit_price` DECIMAL(10, 2) NOT NULL,
  `total_price` DECIMAL(10, 2) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_cart` (`cart_id`),
  KEY `fk_product` (`product_id`),
  CONSTRAINT `fk_cart`
    FOREIGN KEY (`cart_id`)
    REFERENCES `carts` (`id`),
  CONSTRAINT `fk_product`
    FOREIGN KEY (`product_id`)
    REFERENCES `products` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

ALTER TABLE `users`
ADD COLUMN `cart_id` INT DEFAULT NULL,
ADD CONSTRAINT `fk_cart_user`
  FOREIGN KEY (`cart_id`)
  REFERENCES `carts` (`id`);

