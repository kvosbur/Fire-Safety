create database firesafetydb;
use firesafetydb;

CREATE USER 'firesafetyuser'@'localhost' IDENTIFIED BY 'cyvhy+Se*P4/F7zW';

CREATE TABLE `problem` (
	`problem_id` INT NOT NULL AUTO_INCREMENT,
	`response` text NOT NULL,
	`problem_type` int NOT NULL,
	PRIMARY KEY (`problem_id`)
);

CREATE TABLE `condition` (
	`condition_id` INT NOT NULL AUTO_INCREMENT,
	`problem_id` INT NOT NULL,
	`status` INT NOT NULL,
	`concept` varchar(100) NOT NULL,
	PRIMARY KEY (`condition_id`)
);

ALTER TABLE `condition` ADD CONSTRAINT `condition_fk0` FOREIGN KEY (`problem_id`) REFERENCES `problem`(`problem_id`);

GRANT ALL PRIVILEGES ON firesafetydb.* TO 'firesafetyuser'@'localhost';
