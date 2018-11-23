-- phpMyAdmin SQL Dump
-- version 4.7.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Nov 23, 2018 at 11:49 AM
-- Server version: 10.1.30-MariaDB
-- PHP Version: 7.2.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `warehouse`
--

-- --------------------------------------------------------

--
-- Table structure for table `currency_summary`
--

CREATE TABLE `currency_summary` (
  `id` int(11) NOT NULL,
  `created_date` datetime DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  `currencyisocode` varchar(255) DEFAULT NULL,
  `deal_counts` bigint(20) DEFAULT NULL,
  `invalid_deal_counts` bigint(20) DEFAULT NULL,
  `valid_deal_counts` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `deal`
--

CREATE TABLE `deal` (
  `id` int(11) NOT NULL,
  `created_date` datetime DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  `file_name` varchar(255) DEFAULT NULL,
  `process_duration` bigint(20) DEFAULT NULL,
  `invalid_record_size` bigint(20) DEFAULT NULL,
  `valid_record_size` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `invalid_record`
--

CREATE TABLE `invalid_record` (
  `id` int(11) NOT NULL,
  `created_date` datetime DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  `deal_amount` varchar(255) DEFAULT NULL,
  `ordering_currency` varchar(255) DEFAULT NULL,
  `record_id` varchar(255) DEFAULT NULL,
  `to_currency` varchar(255) DEFAULT NULL,
  `deal_id` int(11) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `valid_record`
--

CREATE TABLE `valid_record` (
  `id` int(11) NOT NULL,
  `created_date` datetime DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  `deal_amount` varchar(255) DEFAULT NULL,
  `ordering_currency` varchar(255) DEFAULT NULL,
  `record_id` varchar(255) DEFAULT NULL,
  `to_currency` varchar(255) DEFAULT NULL,
  `deal_id` int(11) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `currency_summary`
--
ALTER TABLE `currency_summary`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `deal`
--
ALTER TABLE `deal`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `invalid_record`
--
ALTER TABLE `invalid_record`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKgics6i2viif1unilc0gcdipsc` (`deal_id`);

--
-- Indexes for table `valid_record`
--
ALTER TABLE `valid_record`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK8chrfdksym67pt09c1ndsgdnn` (`deal_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `currency_summary`
--
ALTER TABLE `currency_summary`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=173;

--
-- AUTO_INCREMENT for table `deal`
--
ALTER TABLE `deal`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `invalid_record`
--
ALTER TABLE `invalid_record`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `valid_record`
--
ALTER TABLE `valid_record`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=99997;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
