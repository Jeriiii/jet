-- phpMyAdmin SQL Dump
-- version 3.2.4
-- http://www.phpmyadmin.net
--
-- Počítač: localhost
-- Vygenerováno: Sobota 29. března 2014, 17:15
-- Verze MySQL: 5.1.44
-- Verze PHP: 5.3.1

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Databáze: `pomvalidator`
--

-- Exportování struktury databáze pro
CREATE DATABASE IF NOT EXISTS `pomvalidator` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `pomvalidator`;

-- --------------------------------------------------------

--
-- Struktura tabulky `pom_items`
--

CREATE TABLE IF NOT EXISTS `pom_items` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `email` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Vypisuji data pro tabulku `pom_items`
--


-- --------------------------------------------------------

--
-- Struktura tabulky `pom_results`
--

CREATE TABLE IF NOT EXISTS `pom_results` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `result` longtext NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Vypisuji data pro tabulku `pom_results`
--

