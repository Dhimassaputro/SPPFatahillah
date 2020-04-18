-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               10.1.31-MariaDB - mariadb.org binary distribution
-- Server OS:                    Win32
-- HeidiSQL Version:             10.3.0.5771
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Dumping database structure for pembayaran
DROP DATABASE IF EXISTS `pembayaran`;
CREATE DATABASE IF NOT EXISTS `pembayaran` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `pembayaran`;

-- Dumping structure for table pembayaran.konfigurasi
DROP TABLE IF EXISTS `konfigurasi`;
CREATE TABLE IF NOT EXISTS `konfigurasi` (
  `nama` varchar(15) NOT NULL,
  `alamat` varchar(20) NOT NULL,
  `telepon` varchar(20) NOT NULL,
  `nama_bendaharal` varchar(30) DEFAULT NULL,
  `tanggal_jatuh_tempo` int(11) NOT NULL DEFAULT '0',
  `dibuat` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `diubah` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `pengguna` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.

-- Dumping structure for table pembayaran.operator
DROP TABLE IF EXISTS `operator`;
CREATE TABLE IF NOT EXISTS `operator` (
  `pengguna` varchar(10) NOT NULL,
  `sandi` varchar(10) DEFAULT NULL,
  `nama` varchar(30) DEFAULT NULL,
  `tipe` varchar(10) DEFAULT NULL,
  `status` varchar(10) DEFAULT NULL,
  `dibuat` datetime DEFAULT CURRENT_TIMESTAMP,
  `diubah` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `pembuat` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`pengguna`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.

-- Dumping structure for table pembayaran.siswa
DROP TABLE IF EXISTS `siswa`;
CREATE TABLE IF NOT EXISTS `siswa` (
  `nis` varchar(10) NOT NULL,
  `nisn` varchar(20) DEFAULT NULL,
  `nama` varchar(50) DEFAULT NULL,
  `alamat` varchar(50) DEFAULT NULL,
  `status` varchar(10) DEFAULT NULL,
  `pengguna` varchar(10) DEFAULT NULL,
  `dibuat` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `diubah` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`nis`),
  KEY `pengguna` (`pengguna`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.

-- Dumping structure for table pembayaran.spp
DROP TABLE IF EXISTS `spp`;
CREATE TABLE IF NOT EXISTS `spp` (
  `kelas` varchar(20) NOT NULL,
  `biaya` varchar(20) NOT NULL,
  `dibuat` int(11) DEFAULT NULL,
  `diubah` int(11) DEFAULT NULL,
  `pengguna` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`kelas`),
  KEY `FK_spp_operator` (`pengguna`),
  CONSTRAINT `FK_spp_operator` FOREIGN KEY (`pengguna`) REFERENCES `operator` (`pengguna`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.

-- Dumping structure for table pembayaran.transaksi
DROP TABLE IF EXISTS `transaksi`;
CREATE TABLE IF NOT EXISTS `transaksi` (
  `nis` varchar(15) NOT NULL,
  `tanggal` date NOT NULL,
  `kode_kelas` varchar(20) NOT NULL,
  `biaya` double NOT NULL DEFAULT '0',
  `pengguna` varchar(10) DEFAULT NULL,
  `kembali` bigint(20) NOT NULL,
  `bulan` int(11) DEFAULT NULL,
  `tahun` int(11) DEFAULT NULL,
  `dibuat` datetime DEFAULT CURRENT_TIMESTAMP,
  KEY `FK_transaksi_operator` (`pengguna`),
  CONSTRAINT `FK_transaksi_operator` FOREIGN KEY (`pengguna`) REFERENCES `operator` (`pengguna`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Data exporting was unselected.

-- Dumping structure for view pembayaran.v_transaksi_pembayaran
DROP VIEW IF EXISTS `v_transaksi_pembayaran`;
-- Creating temporary table to overcome VIEW dependency errors
CREATE TABLE `v_transaksi_pembayaran` (
	`nis` VARCHAR(15) NOT NULL COLLATE 'latin1_swedish_ci',
	`kode_kelas` VARCHAR(20) NOT NULL COLLATE 'latin1_swedish_ci',
	`bulan` INT(11) NULL,
	`tahun` INT(11) NULL,
	`biaya` DOUBLE NOT NULL,
	`dibuat` DATETIME NULL
) ENGINE=MyISAM;

-- Dumping structure for view pembayaran.v_transaksi_pembayaran
DROP VIEW IF EXISTS `v_transaksi_pembayaran`;
-- Removing temporary table and create final VIEW structure
DROP TABLE IF EXISTS `v_transaksi_pembayaran`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `v_transaksi_pembayaran` AS SELECT nis, kode_kelas, bulan, tahun, biaya, dibuat FROM transaksi ;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
