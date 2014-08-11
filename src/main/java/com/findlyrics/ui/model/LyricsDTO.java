package com.findlyrics.ui.model;

import com.findlyrics.db.model.Artist;
import com.findlyrics.db.model.Song;
import com.findlyrics.rest.model.SongPojo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Padonag on 06.08.2014.
 */
public class LyricsDTO {
    private List<DBEntryDTO> searchResults = new ArrayList<DBEntryDTO>();

    public LyricsDTO() {
    }




    public LyricsDTO(ArrayList<SongPojo> inputData) {
        for (SongPojo currentSong : inputData) {
            Artist newArtist = new Artist(currentSong.getArtist().getName());
            Song newSong = new Song(currentSong.getTitle(), currentSong.getUrl());
            DBEntryDTO tempResult = new DBEntryDTO(newArtist, newSong);
            searchResults.add(tempResult);
        }

    }

    public List<DBEntryDTO> getSearchResults() {
        return searchResults;
    }

    public void setSearchResults(List<DBEntryDTO> searchResults) {
        this.searchResults = searchResults;
    }
}


