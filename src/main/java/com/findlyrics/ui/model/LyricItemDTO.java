package com.findlyrics.ui.model;


import com.findlyrics.db.model.Artist;
import com.findlyrics.db.model.Song;

/**
 * Created by Padonag on 19.07.2014.
 */
public class LyricItemDTO {
    private String artistName;
    private String songName;
    private String lyrics;

    public LyricItemDTO(Artist artist, Song song){
        this.artistName = artist.getName();
        this.songName = song.getTitle();
        this.lyrics = song.getLyrics();

    }

    public LyricItemDTO(String artist, String song, String lyrics) {
        this.artistName = artist;
        this.songName = song;
        this.lyrics = lyrics;
    }

    public String getArtistName() {
        return artistName;
    }

    public String getSongName() {
        return songName;
    }

    public String getLyrics() {
        return lyrics;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LyricItemDTO)) return false;

        LyricItemDTO that = (LyricItemDTO) o;

        if (!artistName.equals(that.artistName)) return false;
        if (!lyrics.equals(that.lyrics)) return false;
        if (!songName.equals(that.songName)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = artistName.hashCode();
        result = 31 * result + songName.hashCode();
        result = 31 * result + lyrics.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "LyricItemDTO{" +
                "artistName='" + artistName + '\'' +
                ", songName='" + songName + '\'' +
                ", lyrics='" + lyrics + '\'' +
                '}';
    }
}

