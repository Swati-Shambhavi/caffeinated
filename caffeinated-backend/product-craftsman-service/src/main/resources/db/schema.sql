use caffeinated_inventory;

CREATE TABLE `categories` (
   `id` int NOT NULL AUTO_INCREMENT,
   `name` varchar(255) NOT NULL,
   `created_at` timestamp NOT NULL,
   `updated_at` timestamp NULL DEFAULT NULL,
   PRIMARY KEY (`id`)
 ) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `products` (
   `id` int NOT NULL AUTO_INCREMENT,
   `name` varchar(255) NOT NULL,
   `description` text NOT NULL,
   `price` double NOT NULL,
   `stock_quantity` int NOT NULL,
   `available` tinyint(1) NOT NULL,
   `category_id` int NOT NULL,
   `discount_percentage` double DEFAULT NULL,
   `discount_start_date` date DEFAULT NULL,
   `discount_end_date` date DEFAULT NULL,
   `created_at` timestamp NOT NULL,
   `updated_at` timestamp NULL DEFAULT NULL,
   `image_path` varchar(255) DEFAULT NULL,
   PRIMARY KEY (`id`),
   KEY `category_id` (`category_id`),
   CONSTRAINT `products_ibfk_1` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`)
 ) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
