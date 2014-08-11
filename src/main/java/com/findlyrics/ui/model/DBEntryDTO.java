package com.findlyrics.ui.model;


import com.findlyrics.db.model.Artist;
import com.findlyrics.db.model.Song;

/**
 * Created by Padonag on 19.07.2014.
 */
public class DBEntryDTO {
    private String artistName;
    private String songName;
    private String lyrics;

    public DBEntryDTO(Artist artist, Song song){
        this.artistName = artist.getName();
        this.songName = song.getTitle();
        this.lyrics = song.getLyrics();

    }

    public DBEntryDTO(String artist, String song, String lyrics) {
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


}

