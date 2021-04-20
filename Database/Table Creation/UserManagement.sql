-- Users DB creation query
CREATE DATABASE userdb;

-- User Agreement table creation
CREATE TABLE `userdb`.`user_agreement` (
  `agreement_id` INT NOT NULL AUTO_INCREMENT,
  `description` TEXT(1000) NULL,
  `file_location` VARCHAR(2083) NULL,
  `created_at` DATE NOT NULL,
  `updated_at` DATE NOT NULL,
  PRIMARY KEY (`agreement_id`)
);

-- Researcher table creation
CREATE TABLE `userdb`.`researcher` (
  `user_id` INT NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(20) NOT NULL,
  `last_name` VARCHAR(20) NULL,
  `user_phone` VARCHAR(15) NULL,
  `user_agreement` INT NOT NULL,
  `user_email` VARCHAR(200) NOT NULL,
  `password` VARCHAR(20) NOT NULL,
  `account_status` VARCHAR(10) NOT NULL DEFAULT 'active',
  `created_at` DATE NOT NULL,
  `updated_at` DATE NOT NULL,
  PRIMARY KEY (`user_id`),
  INDEX `user_agreement_idx` (`user_agreement` ASC) VISIBLE,
  CONSTRAINT `researcher_fk`
    FOREIGN KEY (`user_agreement`)
    REFERENCES `userdb`.`user_agreement` (`agreement_id`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE
);

-- Research Field table creation
CREATE TABLE `userdb`.`research_field` (
  `field_id` INT NOT NULL AUTO_INCREMENT,
  `field_name` VARCHAR(100) NOT NULL,
  `description` TEXT(1000) NULL,
  `created_at` DATE NOT NULL,
  `updated_at` DATE NOT NULL,
  PRIMARY KEY (`field_id`)
);
            
-- Researcher - Research Field table creation
CREATE TABLE `userdb`.`researcher_field` (
  `user_id` INT NOT NULL,
  `field_id` INT NOT NULL,
  `created_at` DATE NOT NULL,
  `updated_at` DATE NOT NULL,
  PRIMARY KEY (`user_id`, `field_id`),
  INDEX `researcher_field_fk2_idx` (`field_id` ASC) VISIBLE,
  CONSTRAINT `researcher_field_fk1`
    FOREIGN KEY (`user_id`)
    REFERENCES `userdb`.`researcher` (`user_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `researcher_field_fk2`
    FOREIGN KEY (`field_id`)
    REFERENCES `userdb`.`research_field` (`field_id`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE
);

-- Funding Body table creation
CREATE TABLE `userdb`.`funder` (
  `user_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NOT NULL,
  `user_phone` VARCHAR(15) NULL,
  `user_type` VARCHAR(20) NULL,
  `address` VARCHAR(150) NULL,
  `user_agreement` INT NOT NULL,
  `user_email` VARCHAR(200) NOT NULL,
  `password` VARCHAR(20) NOT NULL,
  `account_status` VARCHAR(10) NOT NULL DEFAULT 'active',
  `created_at` DATE NOT NULL,
  `updated_at` DATE NOT NULL,
  PRIMARY KEY (`user_id`),
  INDEX `user_agreement_idx` (`user_agreement` ASC) VISIBLE,
  CONSTRAINT `funder_fk`
    FOREIGN KEY (`user_agreement`)
    REFERENCES `userdb`.`user_agreement` (`agreement_id`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE
);

-- Buyer table creation
CREATE TABLE `userdb`.`buyer` (
  `user_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NOT NULL,
  `user_phone` VARCHAR(15) NULL,
  `user_agreement` INT NOT NULL,
  `user_email` VARCHAR(200) NOT NULL,
  `password` VARCHAR(20) NOT NULL,
  `account_status` VARCHAR(10) NOT NULL DEFAULT 'active',
  `created_at` DATE NOT NULL,
  `updated_at` DATE NOT NULL,
  PRIMARY KEY (`user_id`),
  INDEX `user_agreement_idx` (`user_agreement` ASC) VISIBLE,
  CONSTRAINT `buyer_fk`
    FOREIGN KEY (`user_agreement`)
    REFERENCES `userdb`.`user_agreement` (`agreement_id`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE
);

-- GB Management table creation
CREATE TABLE `userdb`.`gb_member` (
  `user_id` INT NOT NULL AUTO_INCREMENT,
  `employee_id` INT NOT NUll,
  `first_name` VARCHAR(20) NOT NULL,
  `last_name` VARCHAR(20) NULL,
  `member_type` VARCHAR(20) NULL,
  `user_phone` VARCHAR(15) NULL,
  `user_email` VARCHAR(200) NOT NULL,
  `password` VARCHAR(20) NOT NULL,
  `account_status` VARCHAR(10) NOT NULL DEFAULT 'active',
  `created_at` DATE NOT NULL,
  `updated_at` DATE NOT NULL,
  PRIMARY KEY (`user_id`)
);

-- Administrator table creation
CREATE TABLE `userdb`.`administrator` (
  `user_id` INT NOT NULL AUTO_INCREMENT,
  `employee_id` INT NOT NUll,
  `first_name` VARCHAR(20) NOT NULL,
  `last_name` VARCHAR(20) NULL,
  `user_phone` VARCHAR(15) NULL,
  `user_email` VARCHAR(200) NOT NULL,
  `password` VARCHAR(20) NOT NULL,
  `account_status` VARCHAR(10) NOT NULL DEFAULT 'active',
  `created_at` DATE NOT NULL,
  `updated_at` DATE NOT NULL,
  PRIMARY KEY (`user_id`)
);
