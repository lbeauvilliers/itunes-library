DROP DATABASE jenny_project;

CREATE DATABASE jenny_project;

USE jenny_project;

CREATE TABLE `jenny_project`.`users` (
  `library_persistent_id` VARCHAR(45) NOT NULL,
  `user_name` VARCHAR(45) NOT NULL,
  `user_password` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`library_persistent_id`));

CREATE TABLE `jenny_project`.`songs` (
	`song_id` VARCHAR(45) NOT NULL,
    `song_name` VARCHAR(100),
    `artist` VARCHAR(100),
    `album` VARCHAR(100),
    `library_persistent_id` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`song_id`)
);

CREATE TABLE `jenny_project`.`playlists` (
  `playlist_id` VARCHAR(45) NOT NULL,
  `playlist_name` VARCHAR(100),
  `library_persistent_id` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`playlist_id`)
);
  
CREATE TABLE `jenny_project`.`playlist_songs` (
  `playlist_id` VARCHAR(45) NOT NULL,
  `song_id` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`playlist_id`,`song_id`),
  CONSTRAINT `fk_playlist` FOREIGN KEY (`playlist_id`)
  REFERENCES `playlists`(`playlist_id`),
  CONSTRAINT `fk_song` FOREIGN KEY (`song_id`)
  REFERENCES `songs`(`song_id`)
);
  
SELECT * FROM `jenny_project`.`songs`;
SELECT * FROM `jenny_project`.`playlists`;
SELECT * FROM `jenny_project`.`users`;
SELECT * FROM `jenny_project`.`playlist_songs`;

DROP TABLE `jenny_project`.`playlist_songs`;
DROP TABLE `jenny_project`.`songs`;
DROP TABLE `jenny_project`.`playlists`;
DROP TABLE `jenny_project`.`users`;

  
