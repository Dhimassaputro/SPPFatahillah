-- phpMyAdmin SQL Dump
-- version 4.7.9
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 18, 2020 at 03:47 AM
-- Server version: 10.1.31-MariaDB
-- PHP Version: 7.2.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `pembayaran`
--

-- --------------------------------------------------------

--
-- Table structure for table `konfigurasi`
--

CREATE TABLE `konfigurasi` (
  `nama` varchar(15) NOT NULL,
  `alamat` varchar(20) NOT NULL,
  `telepon` varchar(20) NOT NULL,
  `nama_bendaharal` varchar(30) DEFAULT NULL,
  `tanggal_jatuh_tempo` int(11) NOT NULL DEFAULT '0',
  `dibuat` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `diubah` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `pengguna` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `operator`
--

CREATE TABLE `operator` (
  `pengguna` varchar(10) NOT NULL,
  `sandi` varchar(10) DEFAULT NULL,
  `nama` varchar(30) DEFAULT NULL,
  `tipe` varchar(20) DEFAULT NULL,
  `status` varchar(20) DEFAULT NULL,
  `dibuat` datetime DEFAULT CURRENT_TIMESTAMP,
  `diubah` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `pembuat` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `operator`
--

INSERT INTO `operator` (`pengguna`, `sandi`, `nama`, `tipe`, `status`, `dibuat`, `diubah`, `pembuat`) VALUES
('aji', '1234', 'Aji Syafiiqurrahman', 'Administrator', 'Aktif', '2020-03-17 15:19:58', '2020-03-17 15:19:58', 'Aji Syaf'),
('rohis', 'tsany', 'Rihis', 'Administrator', 'Aktif', '2020-04-07 18:12:19', '2020-04-07 18:12:19', 'Rohis');

-- --------------------------------------------------------

--
-- Table structure for table `siswa`
--

CREATE TABLE `siswa` (
  `nis` varchar(10) NOT NULL,
  `nisn` varchar(20) DEFAULT NULL,
  `nama` varchar(50) DEFAULT NULL,
  `alamat` varchar(50) DEFAULT NULL,
  `status` varchar(20) DEFAULT NULL,
  `pengguna` varchar(10) DEFAULT NULL,
  `dibuat` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `diubah` datetime DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `siswa`
--

INSERT INTO `siswa` (`nis`, `nisn`, `nama`, `alamat`, `status`, `pengguna`, `dibuat`, `diubah`) VALUES
('2016', '2017', 'Dhimas', 'Aktif', 'Jl.Bangka', 'aji', '2020-03-17 15:27:51', '2020-03-17 15:27:51'),
('2018', '2019', 'Rohis', 'Aktif', 'Jl. Buncit', 'aji', '2020-03-26 14:26:55', '2020-03-26 14:26:55'),
('2019', '2020', 'Diki', 'Aktif', 'Buncit', 'rohis', '2020-04-07 18:13:15', '2020-04-07 18:13:15');

-- --------------------------------------------------------

--
-- Table structure for table `spp`
--

CREATE TABLE `spp` (
  `kelas` varchar(20) NOT NULL,
  `biaya` varchar(20) NOT NULL,
  `dibuat` datetime DEFAULT NULL,
  `diubah` datetime DEFAULT NULL,
  `pengguna` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `spp`
--

INSERT INTO `spp` (`kelas`, `biaya`, `dibuat`, `diubah`, `pengguna`) VALUES
('1', '250000', NULL, NULL, NULL),
('2', '300000', NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `transaksi`
--

CREATE TABLE `transaksi` (
  `nis` varchar(15) NOT NULL,
  `nisn` varchar(15) NOT NULL,
  `nama` varchar(50) NOT NULL,
  `kode_kelas` varchar(15) NOT NULL,
  `biaya` double NOT NULL DEFAULT '0',
  `pengguna` varchar(10) DEFAULT NULL,
  `kembali` bigint(20) NOT NULL,
  `bulan` int(11) DEFAULT NULL,
  `tahun` int(11) DEFAULT NULL,
  `dibuat` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Stand-in structure for view `v_transaksi_pembayaran`
-- (See below for the actual view)
--
CREATE TABLE `v_transaksi_pembayaran` (
`nis` varchar(15)
,`kode_kelas` varchar(15)
,`pengguna` varchar(10)
,`bulan` int(11)
,`tahun` int(11)
,`biaya` double
,`dibuat` datetime
);

-- --------------------------------------------------------

--
-- Structure for view `v_transaksi_pembayaran`
--
DROP TABLE IF EXISTS `v_transaksi_pembayaran`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `v_transaksi_pembayaran`  AS  select `transaksi`.`nis` AS `nis`,`transaksi`.`kode_kelas` AS `kode_kelas`,`transaksi`.`pengguna` AS `pengguna`,`transaksi`.`bulan` AS `bulan`,`transaksi`.`tahun` AS `tahun`,`transaksi`.`biaya` AS `biaya`,`transaksi`.`dibuat` AS `dibuat` from `transaksi` ;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `operator`
--
ALTER TABLE `operator`
  ADD PRIMARY KEY (`pengguna`);

--
-- Indexes for table `siswa`
--
ALTER TABLE `siswa`
  ADD PRIMARY KEY (`nis`),
  ADD KEY `pengguna` (`pengguna`);

--
-- Indexes for table `spp`
--
ALTER TABLE `spp`
  ADD PRIMARY KEY (`kelas`),
  ADD KEY `FK_spp_operator` (`pengguna`);

--
-- Indexes for table `transaksi`
--
ALTER TABLE `transaksi`
  ADD KEY `FK_transaksi_operator` (`pengguna`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `spp`
--
ALTER TABLE `spp`
  ADD CONSTRAINT `FK_spp_operator` FOREIGN KEY (`pengguna`) REFERENCES `operator` (`pengguna`);

--
-- Constraints for table `transaksi`
--
ALTER TABLE `transaksi`
  ADD CONSTRAINT `FK_transaksi_operator` FOREIGN KEY (`pengguna`) REFERENCES `operator` (`pengguna`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
