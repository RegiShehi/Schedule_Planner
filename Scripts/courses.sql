-- phpMyAdmin SQL Dump
-- version 4.0.4
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Dec 17, 2014 at 11:49 AM
-- Server version: 5.6.12-log
-- PHP Version: 5.4.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `courseplanner`
--

-- --------------------------------------------------------

--
-- Table structure for table `courses`
--

CREATE TABLE IF NOT EXISTS `courses` (
  `pid` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `time` text NOT NULL,
  `room` text NOT NULL,
  `prof` text NOT NULL,
  `description` text,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`pid`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=7 ;

--
-- Dumping data for table `courses`
--

INSERT INTO `courses` (`pid`, `name`, `time`, `room`, `prof`, `description`, `created_at`, `updated_at`) VALUES
(1, 'C++', 'TF 14:15 - 15:30', 'MB 112', 'Boytcheva', 'C++', '2014-10-01 15:14:13', '0000-00-00 00:00:00'),
(2, 'VB', 'MW 10:45 - 12:00', 'MB 110', 'Bonev', 'Visual Basic course', '2014-10-01 15:15:34', '0000-00-00 00:00:00'),
(3, 'Web Server Tech.', 'MW 9:00 - 10:15', 'MB 116', 'Galletly', 'PHP and ASP.NET', '2014-12-01 17:00:25', '0000-00-00 00:00:00'),
(4, 'Bible as Literature', 'MW 10:45 - 12:00', 'MB 210', 'Ivanov', 'Study the Bible and its Hole texts', '2014-12-07 16:27:17', '0000-00-00 00:00:00'),
(6, 'C++', '', '', '', '', '2014-12-10 07:14:53', '0000-00-00 00:00:00');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
