package com.findlyrics.db.model;


/**
 * Created by Padonag on 19.07.2014.
 */
public class SearchResult {
    private String artistName;
    private String songName;
    private String lyrics;

    public SearchResult(Artist artist, Song song){
        this.artistName = artist.getName();
        this.songName = song.getTitle();
        this.lyrics = song.getLyrics();

    }

    public SearchResult(String artist, String song, String lyrics) {
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

