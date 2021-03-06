CREATE TABLE artists (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  artist_name tinytext CHARACTER SET utf8 NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE songs (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  artist_id bigint(20) DEFAULT NULL,
  song_name tinytext NOT NULL,
  lyrics text NOT NULL,
  PRIMARY KEY (id),
  KEY art_id_idx (artist_id),
  CONSTRAINT id_art FOREIGN KEY (artist_id) REFERENCES artists (id) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
