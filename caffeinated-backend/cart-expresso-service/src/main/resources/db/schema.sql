CREATE TABLE `carts` (
   `id` int NOT NULL AUTO_INCREMENT,
   `total_price` decimal(10,2) NOT NULL DEFAULT '0.00',
   `created_at` timestamp NOT NULL,
   `updated_at` timestamp NULL DEFAULT NULL,
   `user_id` int NOT NULL,
   PRIMARY KEY (`id`)
 ) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci

CREATE TABLE `cart_items` (
   `id` int NOT NULL AUTO_INCREMENT,
   `cart_id` int NOT NULL,
   `product_id` int NOT NULL,
   `product_name` varchar(255) NOT NULL,
   `product_quantity` int NOT NULL,
   `unit_price` decimal(10,2) NOT NULL,
   `total_price` decimal(10,2) NOT NULL,
   PRIMARY KEY (`id`),
   KEY `fk_cart` (`cart_id`),
   CONSTRAINT `fk_cart` FOREIGN KEY (`cart_id`) REFERENCES `carts` (`id`)
 ) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci