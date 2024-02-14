use caffeinated_user;

CREATE TABLE `address` (
   `id` int NOT NULL AUTO_INCREMENT,
   `address1` varchar(200) NOT NULL,
   `address2` varchar(200) DEFAULT NULL,
   `city` varchar(50) NOT NULL,
   `state` varchar(50) NOT NULL,
   `pin_code` int NOT NULL,
   `country` varchar(50) NOT NULL,
   `created_at` timestamp NOT NULL,
   `updated_at` timestamp NULL DEFAULT NULL,
   PRIMARY KEY (`id`)
 ) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `users` (
   `id` int NOT NULL AUTO_INCREMENT,
   `email` varchar(255) NOT NULL,
   `role` varchar(50) NOT NULL,
   `name` varchar(100) NOT NULL,
   `mobile_number` varchar(20) NOT NULL,
   `address_id` int DEFAULT NULL,
   `created_at` timestamp NOT NULL,
   `updated_at` timestamp NULL DEFAULT NULL,
   PRIMARY KEY (`id`),
   KEY `address_id` (`address_id`),
   CONSTRAINT `users_ibfk_1` FOREIGN KEY (`address_id`) REFERENCES `address` (`id`)
 ) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci