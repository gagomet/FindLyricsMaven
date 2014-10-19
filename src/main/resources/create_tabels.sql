CREATE TABLE artists (
  id          BIGINT(20)         NOT NULL AUTO_INCREMENT,
  artist_name TINYTEXT
              CHARACTER SET utf8 NOT NULL,
  PRIMARY KEY (id)
)
  ENGINE =InnoDB
  DEFAULT CHARSET =utf8;


CREATE TABLE songs (
  id        BIGINT(20) NOT NULL AUTO_INCREMENT,
  artist_id BIGINT(20) DEFAULT NULL,
  song_name TINYTEXT   NOT NULL,
  lyrics    TEXT       NOT NULL,
  PRIMARY KEY (id),
  KEY art_id_idx (artist_id),
  CONSTRAINT id_art FOREIGN KEY (artist_id) REFERENCES artists (id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
)
  ENGINE =InnoDB
  DEFAULT CHARSET =utf8;
