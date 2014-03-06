-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               5.6.10-log - MySQL Community Server (GPL)
-- Server OS:                    Win64
-- HeidiSQL version:             7.0.0.4053
-- Date/time:                    2014-03-06 15:45:03
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET FOREIGN_KEY_CHECKS=0 */;

-- Dumping database structure for pomvalidator
CREATE DATABASE IF NOT EXISTS `pomvalidator` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `pomvalidator`;


-- Dumping structure for table pomvalidator.test
CREATE TABLE IF NOT EXISTS `test` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL DEFAULT '0',
  `age` int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- Dumping data for table pomvalidator.test: ~1 rows (approximately)
/*!40000 ALTER TABLE `test` DISABLE KEYS */;
INSERT INTO `test` (`id`, `name`, `age`) VALUES
	(1, 'prase', 5);
/*!40000 ALTER TABLE `test` ENABLE KEYS */;
/*!40014 SET FOREIGN_KEY_CHECKS=1 */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
