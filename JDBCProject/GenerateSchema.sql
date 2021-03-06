-- MySQL Workbench Forward Engineering
drop database simple_company;
SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema simple_company
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema simple_company
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `simple_company` DEFAULT CHARACTER SET utf8 ;
USE `simple_company` ;

-- -----------------------------------------------------
-- Table `simple_company`.`Product`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `simple_company`.`Product` (
  `id` INT NOT NULL,
  `prodName` VARCHAR(45) NOT NULL,
  `prodDescription` VARCHAR(200) NULL,
  `prodCategory` INT NOT NULL,
  `prodUPC` VARCHAR(45) NOT NULL,
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `simple_company`.`Customer`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `simple_company`.`Customer` (
  `id` INT NOT NULL,
  `firstName` VARCHAR(45) NOT NULL,
  `lastName` VARCHAR(45) NOT NULL,
  `gender` CHAR(1) NOT NULL,
  `dob` DATE NOT NULL,
  `email` VARCHAR(50) NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `simple_company`.`Purchase`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `simple_company`.`Purchase` (
  `id` INT NOT NULL,
  `purchaseDate` DATE NOT NULL,
  `purchaseAmount` DECIMAL(8,2) NOT NULL,
  `customerId` INT NOT NULL,
  `productId` INT NOT NULL,
  PRIMARY KEY (`id`, `productId`, `customerId`),
  INDEX `fk_Purchase_Customer_idx` (`customerId` ASC),
  INDEX `fk_Purchase_Product1_idx` (`productId` ASC),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  CONSTRAINT `fk_Purchase_Customer`
    FOREIGN KEY (`customerId`)
    REFERENCES `simple_company`.`Customer` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Purchase_Product1`
    FOREIGN KEY (`productId`)
    REFERENCES `simple_company`.`Product` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `simple_company`.`CreditCard`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `simple_company`.`CreditCard` (
  `name` VARCHAR(45) NOT NULL,
  `ccNumber` VARCHAR(16) NOT NULL,
  `expDate` VARCHAR(25) NOT NULL,
  `securityCode` VARCHAR(3) NOT NULL,
  `customerId` INT NOT NULL,
  PRIMARY KEY (`name`, `customerId`),
  INDEX `fk_CreditCard_Customer1_idx` (`customerId` ASC),
  CONSTRAINT `fk_CreditCard_Customer1`
    FOREIGN KEY (`customerId`)
    REFERENCES `simple_company`.`Customer` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `simple_company`.`Address`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `simple_company`.`Address` (
  `address1` VARCHAR(50) NOT NULL,
  `address2` VARCHAR(50) NULL,
  `city` VARCHAR(45) NOT NULL,
  `state` VARCHAR(25) NOT NULL,
  `zipcode` VARCHAR(10) NOT NULL,
  `customerId` INT NOT NULL,
  PRIMARY KEY (`customerId`),
  CONSTRAINT `fk_Address_Customer1`
    FOREIGN KEY (`customerId`)
    REFERENCES `simple_company`.`Customer` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
