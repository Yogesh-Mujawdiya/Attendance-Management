-- phpMyAdmin SQL Dump
-- version 4.9.4
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Feb 29, 2020 at 10:12 PM
-- Server version: 10.3.16-MariaDB
-- PHP Version: 7.3.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `id12757383_attendance_management`
--

-- --------------------------------------------------------

--
-- Table structure for table `attendance`
--

CREATE TABLE `attendance` (
  `Id` int(11) NOT NULL,
  `Faculty_Id` text NOT NULL,
  `Subject_Name` text NOT NULL,
  `Roll_Number` int(11) NOT NULL,
  `Status` tinyint(1) NOT NULL,
  `Date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `course`
--

CREATE TABLE `course` (
  `Department_Id` varchar(10) NOT NULL,
  `Course_Id` varchar(10) NOT NULL,
  `Department_Name` varchar(20) NOT NULL,
  `Course_Name` varchar(25) NOT NULL,
  `Qualification_Code` int(2) NOT NULL,
  `Student_Count` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `course`
--

INSERT INTO `course` (`Department_Id`, `Course_Id`, `Department_Name`, `Course_Name`, `Qualification_Code`, `Student_Count`) VALUES
('05', '1', 'CA', 'MCA', 2, 115),
('05', '2', 'CA', 'MSC', 2, 25),
('05', '3', 'CA', 'M.Tec', 2, 20);

-- --------------------------------------------------------

--
-- Table structure for table `course_subject`
--

CREATE TABLE `course_subject` (
  `Course_Id` varchar(10) NOT NULL,
  `Department_Id` varchar(20) NOT NULL,
  `Semester` int(11) NOT NULL,
  `Subject_Id` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `course_subject`
--

INSERT INTO `course_subject` (`Course_Id`, `Department_Id`, `Semester`, `Subject_Id`) VALUES
('1', '05', 1, 'CA701'),
('1', '05', 2, 'CA702'),
('1', '05', 1, 'CA703'),
('1', '05', 2, 'CA704'),
('1', '05', 3, 'CA705'),
('1', '05', 4, 'CA706'),
('1', '05', 3, 'CA707'),
('1', '05', 4, 'CA708'),
('1', '05', 5, 'CA709'),
('1', '05', 2, 'CA710'),
('1', '05', 1, 'CA711 '),
('1', '05', 2, 'CA712'),
('1', '05', 1, 'CA713'),
('1', '05', 2, 'CA714'),
('1', '05', 1, 'CA715'),
('1', '05', 2, 'CA716'),
('1', '05', 1, 'CA717'),
('1', '05', 2, 'CA718'),
('1', '05', 1, 'CA719'),
('1', '05', 3, 'CA721'),
('1', '05', 4, 'CA722'),
('1', '05', 3, 'CA723'),
('1', '05', 4, 'CA724'),
('1', '05', 3, 'CA725'),
('1', '05', 4, 'CA726'),
('1', '05', 3, 'CA727'),
('1', '05', 4, 'CA728'),
('1', '05', 3, 'CA729'),
('1', '05', 5, 'CA731'),
('1', '05', 5, 'CA733'),
('1', '05', 5, 'CA749'),
('1', '05', 6, 'CA750'),
('1', '05', 4, 'CA7A2'),
('1', '05', 5, 'CA7B2'),
('1', '05', 5, 'CA7C3'),
('1', '05', 5, 'CA7C7');

-- --------------------------------------------------------

--
-- Table structure for table `faculty`
--

CREATE TABLE `faculty` (
  `Id` varchar(20) NOT NULL,
  `Password` text NOT NULL,
  `Name` varchar(30) NOT NULL,
  `Mobile_Number` varchar(12) NOT NULL,
  `Email` varchar(40) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `faculty`
--

INSERT INTO `faculty` (`Id`, `Password`, `Name`, `Mobile_Number`, `Email`) VALUES
('1', '1', '1', '1', '1'),
('121', '123456', 'Yogesh Kumar ', '7879793064', 'ykmujawdiya@gmail.com');

-- --------------------------------------------------------

--
-- Table structure for table `faculty_subject`
--

CREATE TABLE `faculty_subject` (
  `Faculty_Id` varchar(10) NOT NULL,
  `Subject_Id` varchar(10) NOT NULL,
  `Course_Id` varchar(10) NOT NULL,
  `Department_Id` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `faculty_subject`
--

INSERT INTO `faculty_subject` (`Faculty_Id`, `Subject_Id`, `Course_Id`, `Department_Id`) VALUES
('', 'CA701', '1', '05'),
('1', 'CA701', '1', '05'),
('1', 'CA705', '1', '05'),
('1', 'CA714', '1', '05'),
('1', 'CA7A2', '1', '05'),
('1', 'CA701', '121', '05'),
('121', 'CA701', '1', '05'),
('121', 'CA7C1', '1', '05');

-- --------------------------------------------------------

--
-- Table structure for table `students`
--

CREATE TABLE `students` (
  `Roll_Number` int(11) NOT NULL,
  `Password` text NOT NULL,
  `Name` text NOT NULL,
  `Mobile_No` text NOT NULL,
  `Email` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `students`
--

INSERT INTO `students` (`Roll_Number`, `Password`, `Name`, `Mobile_No`, `Email`) VALUES
(205117050, '123456', '', '', ''),
(205117080, '123456', '', '', ''),
(205117090, '123456', '', '', ''),
(205118090, '123456', 'Yogesh', '7879793064', 'ykmujawdiya@gmail.com'),
(205119090, '123456', '', '', '');

-- --------------------------------------------------------

--
-- Table structure for table `subject`
--

CREATE TABLE `subject` (
  `Id` varchar(10) NOT NULL,
  `Name` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `subject`
--

INSERT INTO `subject` (`Id`, `Name`) VALUES
('CA701', 'Data Structures Lab using C'),
('CA702', 'DBMS Lab'),
('CA703', 'Business Communication'),
('CA704', 'OS Lab'),
('CA705', 'Networks Lab'),
('CA706', 'Web Technology and Its Application Lab'),
('CA707', 'Data Mining Lab'),
('CA708', 'Information Security Lab'),
('CA709', 'Artificial Intelligence Lab'),
('CA710', 'Design and Analysis of Algorithms'),
('CA711', 'Data Structures and Applications'),
('CA712', 'Database Systems'),
('CA713', 'Mathematical Foundations of Computer\r\nApplications'),
('CA714', 'Operating Systems'),
('CA715', 'Computer Organization and Architecture'),
('CA716', 'Object Oriented Programming'),
('CA717', 'Accounting and Financial Management'),
('CA718', 'Resource Management Techniques'),
('CA719', 'Probability and Statistical Methods'),
('CA721', 'Data Mining Techniques'),
('CA722', 'Organizational Behavior'),
('CA723', 'Python and R Programming'),
('CA724', 'Information Security'),
('CA725', 'Software Engineering'),
('CA726', 'Distributed Technology'),
('CA727', 'Computer Networks'),
('CA728', 'Web Technology and Its Applications '),
('CA729', 'Object Oriented Analysis and Design'),
('CA731', 'Artificial Intelligence'),
('CA733', 'Cloud Computing'),
('CA749', 'Mini Project Work'),
('CA750', 'Project Work'),
('CA7A1', 'Business Intelligence'),
('CA7A2', 'Unix and Shell Programming'),
('CA7A3', 'Visual Programming'),
('CA7A4', 'Software Architecture and Project Management'),
('CA7A5', 'Business Ethics'),
('CA7B1', 'Green Computing'),
('CA7B2', 'Image Processing'),
('CA7B3', 'Internet of Things'),
('CA7B4', 'Marketing Management'),
('CA7B5', 'Soft Computing'),
('CA7B6', 'Advanced Database Technology'),
('CA7B7', 'Modeling and Computer Simulation'),
('CA7B8', 'Computer Vision'),
('CA7C1', 'Human Computer Interaction'),
('CA7C2', 'Bioinformatics'),
('CA7C3', 'Deep learning'),
('CA7C4', 'Multi-core Programming'),
('CA7C5', 'MEAN Stack Web Development'),
('CA7C6', 'Big Data Management'),
('CA7C7', 'Evolutionary Computing'),
('CA7C8', 'Social Network Analysis');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `attendance`
--
ALTER TABLE `attendance`
  ADD PRIMARY KEY (`Id`);

--
-- Indexes for table `course`
--
ALTER TABLE `course`
  ADD PRIMARY KEY (`Course_Id`,`Department_Id`);

--
-- Indexes for table `course_subject`
--
ALTER TABLE `course_subject`
  ADD PRIMARY KEY (`Course_Id`,`Department_Id`,`Subject_Id`);

--
-- Indexes for table `faculty`
--
ALTER TABLE `faculty`
  ADD PRIMARY KEY (`Id`);

--
-- Indexes for table `students`
--
ALTER TABLE `students`
  ADD PRIMARY KEY (`Roll_Number`);

--
-- Indexes for table `subject`
--
ALTER TABLE `subject`
  ADD PRIMARY KEY (`Id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `attendance`
--
ALTER TABLE `attendance`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
