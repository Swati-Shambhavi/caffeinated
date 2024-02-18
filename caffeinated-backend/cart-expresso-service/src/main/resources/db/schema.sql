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
 ) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

 CREATE TABLE `orders` (
   `id` int NOT NULL AUTO_INCREMENT,
   `total_price` decimal(10,2) NOT NULL DEFAULT '0.00',
   `created_at` timestamp NOT NULL,
   `updated_at` timestamp NULL DEFAULT NULL,
   `user_id` int NOT NULL,
   `razorpay_payment_id` varchar(255) NOT NULL,
   PRIMARY KEY (`id`)
 ) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
 CREATE TABLE `order_items` (
   `id` int NOT NULL AUTO_INCREMENT,
   `order_id` int NOT NULL,
   `product_id` int NOT NULL,
   `product_name` varchar(255) NOT NULL,
   `product_quantity` int NOT NULL,
   `unit_price` decimal(10,2) NOT NULL,
   `total_price` decimal(10,2) NOT NULL,
   PRIMARY KEY (`id`),
   KEY `fk_order` (`order_id`),
   CONSTRAINT `fk_order` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`)
 ) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
 CREATE TABLE `razorpay_confirmations` (
    `id` int NOT NULL AUTO_INCREMENT,
    `order_id` int NOT NULL,
    `razorpay_order_id` varchar(255) NOT NULL,
    `razorpay_payment_id` varchar(255) NOT NULL,
    `razorpay_signature` varchar(255) NOT NULL,
    PRIMARY KEY (`id`),
    KEY `fk_order_razorpay` (`order_id`),
    CONSTRAINT `fk_order_razorpay` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`)
 ) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


ALTER TABLE `orders` DROP COLUMN `razorpay_payment_id`;