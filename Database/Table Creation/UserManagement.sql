-- Users DB creation query
CREATE DATABASE userdb;

-- User table creation
CREATE TABLE `userdb`.`user` (
  `user_id` INT NOT NULL AUTO_INCREMENT,
  `user_email` VARCHAR(200) NOT NULL,
  `password` VARCHAR(20) NOT NULL,
  `user_role` VARCHAR(10) NOT NULL,
  `account_status` VARCHAR(10) NOT NULL DEFAULT 'active',
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_id`)
);

-- User Agreement table creation
CREATE TABLE `userdb`.`user_agreement` (
  `agreement_id` INT NOT NULL AUTO_INCREMENT,
  `description` TEXT(1000) NULL,
  `file_location` VARCHAR(2083) NULL,
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`agreement_id`)
);

-- Researcher table creation
CREATE TABLE `userdb`.`researcher` (
  `researcher_id` INT NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(20) NOT NULL,
  `last_name` VARCHAR(20) NULL,
  `user_phone` VARCHAR(15) NULL,
  `user_id` INT NOT NULL,
  `user_agreement` INT NOT NULL,
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`researcher_id`),
  INDEX `user_agreement_idx` (`user_agreement` ASC) VISIBLE,
  CONSTRAINT `researcher_fk1`
    FOREIGN KEY (`user_id`)
    REFERENCES `userdb`.`user` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `researcher_fk2`
    FOREIGN KEY (`user_agreement`)
    REFERENCES `userdb`.`user_agreement` (`agreement_id`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE
);

-- Funding Body table creation
CREATE TABLE `userdb`.`funder` (
  `funder_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NOT NULL,
  `user_phone` VARCHAR(15) NULL,
  `user_type` VARCHAR(20) NULL,
  `address` VARCHAR(150) NULL,
  `user_id` INT NOT NULL, 
  `user_agreement` INT NOT NULL,
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`funder_id`),
  INDEX `user_agreement_idx` (`user_agreement` ASC) VISIBLE,
  CONSTRAINT `funder_fk1`
    FOREIGN KEY (`user_id`)
    REFERENCES `userdb`.`user` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `funder_fk2`
    FOREIGN KEY (`user_agreement`)
    REFERENCES `userdb`.`user_agreement` (`agreement_id`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE
);

-- Buyer table creation
CREATE TABLE `userdb`.`buyer` (
  `buyer_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NOT NULL,
  `user_phone` VARCHAR(15) NULL,
  `user_id` INT NOT NULL,
  `user_agreement` INT NOT NULL,
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`buyer_id`),
  INDEX `user_agreement_idx` (`user_agreement` ASC) VISIBLE,
  CONSTRAINT `buyer_fk1`
    FOREIGN KEY (`user_id`)
    REFERENCES `userdb`.`user` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `buyer_fk2`
    FOREIGN KEY (`user_agreement`)
    REFERENCES `userdb`.`user_agreement` (`agreement_id`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE
);

-- GB Management table creation
CREATE TABLE `userdb`.`gb_member` (
  `member_id` INT NOT NULL AUTO_INCREMENT,
  `employee_id` INT NOT NUll,
  `first_name` VARCHAR(20) NOT NULL,
  `last_name` VARCHAR(20) NULL,
  `member_type` VARCHAR(20) NULL,
  `user_phone` VARCHAR(15) NULL,
  `user_id` INT NOT NULL,
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`member_id`),
  CONSTRAINT `member_fk`
    FOREIGN KEY (`user_id`)
    REFERENCES `userdb`.`user` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
);

-- Administrator table creation
CREATE TABLE `userdb`.`administrator` (
  `admin_id` INT NOT NULL AUTO_INCREMENT,
  `employee_id` INT NOT NUll,
  `first_name` VARCHAR(20) NOT NULL,
  `last_name` VARCHAR(20) NULL,
  `user_phone` VARCHAR(15) NULL,
  `user_id` INT NOT NULL,
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`admin_id`),
  CONSTRAINT `admin_fk`
    FOREIGN KEY (`user_id`)
    REFERENCES `userdb`.`user` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
);
