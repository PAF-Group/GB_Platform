-- phpMyAdmin SQL Dump
-- version 5.0.3
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 22, 2021 at 08:26 PM
-- Server version: 10.4.14-MariaDB
-- PHP Version: 7.2.34

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `gb_ordermanagement`
--

-- --------------------------------------------------------

--
-- Table structure for table `orderdetails`
--

CREATE TABLE `orderdetails` (
  `OrderId` int(11) NOT NULL,
  `ProductId` int(11) NOT NULL,
  `sellerId` int(11) NOT NULL,
  `Quantity` int(11) NOT NULL,
  `UnitPrice` double NOT NULL,
  `Status` varchar(20) NOT NULL DEFAULT 'Processing',
  `ShippingDate` date DEFAULT NULL,
  `ShippingCompany` varchar(100) DEFAULT NULL,
  `ShipingTrackId` varchar(100) DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` datetime DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `orderdetails`
--

INSERT INTO `orderdetails` (`OrderId`, `ProductId`, `sellerId`, `Quantity`, `UnitPrice`, `Status`, `ShippingDate`, `ShippingCompany`, `ShipingTrackId`, `created_at`, `updated_at`) VALUES
(1, 1, 0, 2, 200, 'Shipped', '2021-03-21', 'GRASS', '4rsfdsafe', '2021-04-20 02:09:09', '2021-04-20 07:39:09'),
(1, 2, 0, 2, 200, 'Processing', NULL, NULL, NULL, '2021-04-20 02:09:09', '2021-04-20 07:39:09'),
(2, 2, 0, 2, 200, 'Shipped', '2021-03-21', 'GRASS', '4rsfdsafe', '2021-04-20 02:09:09', '2021-04-21 11:11:50'),
(3, 2, 1, 2, 200, 'Processing', NULL, NULL, NULL, '2021-04-20 02:09:09', '2021-04-20 07:39:09'),
(8, 2, 1, 2, 200, 'Processing', NULL, NULL, NULL, '2021-04-21 05:11:49', '2021-04-21 10:41:49'),
(2, 3, 0, 5, 500, 'Processing', NULL, NULL, NULL, '2021-04-20 02:09:09', '2021-04-20 07:39:09'),
(3, 3, 1, 5, 500, 'Processing', NULL, NULL, NULL, '2021-04-20 02:09:09', '2021-04-20 07:39:09'),
(8, 3, 1, 5, 500, 'Processing', NULL, NULL, NULL, '2021-04-21 05:11:49', '2021-04-21 10:41:49');

-- --------------------------------------------------------

--
-- Table structure for table `orderissues`
--

CREATE TABLE `orderissues` (
  `IssueId` int(11) NOT NULL,
  `OrderId` int(11) NOT NULL,
  `ProductId` int(11) NOT NULL,
  `IssueDescription` varchar(500) NOT NULL,
  `Status` varchar(25) NOT NULL DEFAULT 'Not Solved',
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` datetime DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `orderissues`
--

INSERT INTO `orderissues` (`IssueId`, `OrderId`, `ProductId`, `IssueDescription`, `Status`, `created_at`, `updated_at`) VALUES
(2, 2, 1, 'Product was not as described', 'Solved', '2021-04-20 02:09:35', '2021-04-20 07:39:35');

-- --------------------------------------------------------

--
-- Table structure for table `orders`
--

CREATE TABLE `orders` (
  `OrderId` int(11) NOT NULL,
  `BuyerId` int(11) NOT NULL,
  `Status` varchar(20) NOT NULL DEFAULT 'Not Paid',
  `PaymentSlipUrl` varchar(200) DEFAULT NULL,
  `paymentAccepted` varchar(20) DEFAULT NULL,
  `ShippingAddress` varchar(200) NOT NULL,
  `TotalAmount` double DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` datetime DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `orders`
--

INSERT INTO `orders` (`OrderId`, `BuyerId`, `Status`, `PaymentSlipUrl`, `paymentAccepted`, `ShippingAddress`, `TotalAmount`, `created_at`, `updated_at`) VALUES
(2, 2, 'Processing', 'fdsafasfs', 'Pending', '', 2500, '2021-04-20 02:09:21', '2021-04-21 11:11:22'),
(3, 3, 'Not Paid', NULL, NULL, 'No.1, Temple Road, Matara', 700, '2021-04-20 02:09:21', '2021-04-20 07:39:21'),
(4, 3, 'Not Paid', NULL, NULL, 'No.1, Temple Road, Matara', NULL, '2021-04-21 05:06:37', '2021-04-21 10:36:37'),
(5, 3, 'Not Paid', NULL, NULL, 'No.1, Temple Road, Matara', NULL, '2021-04-21 05:07:12', '2021-04-21 10:37:12'),
(7, 3, 'Not Paid', NULL, NULL, 'No.1, Temple Road, Matara', NULL, '2021-04-21 05:11:29', '2021-04-21 10:41:29'),
(8, 3, 'Not Paid', NULL, NULL, 'No.1, Temple Road, Matara', 700, '2021-04-21 05:11:49', '2021-04-21 10:41:49');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `orderdetails`
--
ALTER TABLE `orderdetails`
  ADD PRIMARY KEY (`ProductId`,`OrderId`);

--
-- Indexes for table `orderissues`
--
ALTER TABLE `orderissues`
  ADD PRIMARY KEY (`IssueId`),
  ADD KEY `OrderId` (`OrderId`);

--
-- Indexes for table `orders`
--
ALTER TABLE `orders`
  ADD PRIMARY KEY (`OrderId`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `orderissues`
--
ALTER TABLE `orderissues`
  MODIFY `IssueId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `orders`
--
ALTER TABLE `orders`
  MODIFY `OrderId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `orderissues`
--
ALTER TABLE `orderissues`
  ADD CONSTRAINT `orderissues_ibfk_1` FOREIGN KEY (`OrderId`) REFERENCES `orders` (`OrderId`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
