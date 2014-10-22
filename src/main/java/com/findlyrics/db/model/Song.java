package com.findlyrics.db.model;

import org.apache.log4j.Logger;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by Padonag on 04.08.2014.
 */

@Entity
@Table(name = "songs")
public class Song {
    private static final Logger log = Logger.getLogger(Song.class);
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long songId;
    @ManyToOne(targetEntity = Artist.class)
    @JoinColumn(name = "artist_id")
    private Long artistId;
    @Column(name = "song_name")
    private String title;
    @Column(name = "lyrics")
    @Type(type = "text")
    private String lyrics;

    public Song() {
    }

    public Song(String title, String lyrics) {
        this.title = title;
        this.lyrics = lyrics;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLyrics() {
        return lyrics;
    }

    public void setLyrics(String lyrics) {
        this.lyrics = lyrics;
    }

    public Long getSongId() {
        return songId;
    }

    public void setSongId(Long songId) {
        this.songId = songId;
    }

    public void setArtistId(Long artistId) {
        this.artistId = artistId;
    }

    public Long getArtistId() {
        return artistId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Song)) return false;

        Song song = (Song) o;


        if (!artistId.equals(song.artistId)) return false;
        if (!songId.equals(song.songId)) return false;
        if (!lyrics.equals(song.lyrics)) return false;
        if (!title.equals(song.title)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = songId.hashCode();
        result = 31 * result + artistId.hashCode();
        result = 31 * result + title.hashCode();
        result = 31 * result + lyrics.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Song{" +
                "title='" + title + '\'' +
                '}';
    }
}
