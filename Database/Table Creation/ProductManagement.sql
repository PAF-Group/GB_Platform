-- Create Product Inventory database
CREATE DATABASE `productdb`;

-- Create Product table
CREATE TABLE `productdb`.`product` (
  `product_id` INT NOT NULL AUTO_INCREMENT,
  `product_name` VARCHAR(100) NOT NULL,
  `description` VARCHAR(1000) NULL,
  `unit_price` DOUBLE NOT NULL,
  `category` VARCHAR(50) NOT NULL,
  `status` VARCHAR(10) NOT NULL,
  `seller_id` INT NOT NULL,
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`product_id`));
