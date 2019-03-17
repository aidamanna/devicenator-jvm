CREATE TABLE IF NOT EXISTS `devices` (
  `imei` VARCHAR PRIMARY KEY,
  `vendor` VARCHAR,
  `model` VARCHAR,
  `operatingSystem` VARCHAR,
  `operatingSystemVersion` VARCHAR

)ENGINE=InnoDB DEFAULT CHARSET=UTF8;
