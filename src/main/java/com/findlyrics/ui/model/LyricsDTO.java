package com.findlyrics.ui.model;

import com.findlyrics.db.model.Artist;
import com.findlyrics.db.model.SearchResult;
import com.findlyrics.db.model.Song;
import com.findlyrics.rest.model.SongPojo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Padonag on 06.08.2014.
 */
public class LyricsDTO {
    private List<SearchResult> searchResults = new ArrayList<SearchResult>();

    public LyricsDTO() {
    }

    public LyricsDTO(List<Artist> inputData) {
        for (Artist currentArtist : inputData) {
            for (Song currentSong : currentArtist.getRepertoir()) {
                SearchResult tempResult = new SearchResult(currentArtist, currentSong);
                searchResults.add(tempResult);
            }
        }
    }


    public LyricsDTO(ArrayList<SongPojo> inputData) {
        for (SongPojo currentSong : inputData) {
            Artist newArtist = new Artist(currentSong.getArtist().getName());
            Song newSong = new Song(currentSong.getTitle(), currentSong.getUrl());
            SearchResult tempResult = new SearchResult(newArtist, newSong);
            searchResults.add(tempResult);
        }

    }

    public List<SearchResult> getSearchResults() {
        return searchResults;
    }
}
