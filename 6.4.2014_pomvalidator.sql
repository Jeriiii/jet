-- --------------------------------------------------------
-- Hostitel:                     127.0.0.1
-- Verze serveru:                5.6.15-log - MySQL Community Server (GPL)
-- OS serveru:                   Win64
-- HeidiSQL Verze:               8.2.0.4675
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- Exportování struktury databáze pro
CREATE DATABASE IF NOT EXISTS `pomvalidator` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `pomvalidator`;


-- Exportování struktury pro tabulka pomvalidator.pom_items
CREATE TABLE IF NOT EXISTS `pom_items` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `email` varchar(50) NOT NULL,
  `result` mediumtext,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

-- Exportování dat pro tabulku pomvalidator.pom_items: ~21 rows (přibližně)
/*!40000 ALTER TABLE `pom_items` DISABLE KEYS */;
INSERT INTO `pom_items` (`id`, `email`, `result`) VALUES
	(1, 'test@test.cz', NULL),
	(2, 'test@test.cz', NULL),
	(3, 'test@test.cz', NULL),
	(4, 'test@test.cz', NULL),
	(5, 'test@test.cz', NULL),
	(6, 'test@test.cz', NULL),
	(7, 'test@test.cz', NULL),
	(8, 'test@test.cz', NULL),
	(9, 'test@test.cz', NULL),
	(10, '', NULL),
	(11, '', NULL),
	(12, '', NULL),
	(13, '', NULL),
	(14, '', NULL),
	(15, '', NULL),
	(16, '', NULL),
	(17, '', NULL),
	(18, '', NULL),
	(19, '', NULL),
	(20, '', NULL),
	(21, '', NULL);
/*!40000 ALTER TABLE `pom_items` ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
