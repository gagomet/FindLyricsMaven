CREATE TABLE `artists` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `artist_name` tinytext CHARACTER SET utf8mb4 NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=823 DEFAULT CHARSET=utf8;


CREATE TABLE `songs` (
  `id_songs` bigint(20) NOT NULL AUTO_INCREMENT,
  `id_artist` bigint(20) DEFAULT NULL,
  `song_name` tinytext NOT NULL,
  `lyrics` text NOT NULL,
  PRIMARY KEY (`id_songs`),
  KEY `art_id_idx` (`id_artist`),
  CONSTRAINT `id_art` FOREIGN KEY (`id_artist`) REFERENCES `artists` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6373 DEFAULT CHARSET=utf8;

