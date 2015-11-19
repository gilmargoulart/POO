CREATE DATABASE IF NOT EXISTS `bd_poo_contratos`;

USE `bd_poo_contratos`;

CREATE TABLE IF NOT EXISTS `Contato` (
  `ContatoCodigo` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `ContatoNome` VARCHAR(100) NULL,
  `ContatoEndereco` VARCHAR(120) NULL,
  `ContatoNumeroTelefone` VARCHAR(25) NULL,
  `ContatoEmail` VARCHAR(60) NULL,
  PRIMARY KEY (`ContatoCodigo`));
