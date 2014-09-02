package com.findlyrics.db.model;

import com.findlyrics.db.dao.impl.SongDAO;
import org.apache.log4j.Logger;

/**
 * Created by Padonag on 04.08.2014.
 */
public class Song {
    private static final Logger log = Logger.getLogger(SongDAO.class);
    private Long id;
    private Long artistId;
    private String title;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        if (!id.equals(song.id)) return false;
        if (!lyrics.equals(song.lyrics)) return false;
        if (!title.equals(song.title)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
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
