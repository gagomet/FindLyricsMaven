package com.findlyrics.ui.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Padonag on 06.08.2014.
 */
public class LyricsDTO {
    private List<LyricItemDTO> searchResults = new ArrayList<LyricItemDTO>();

    public LyricsDTO() {
    }
    public List<LyricItemDTO> getSearchResults() {
        return searchResults;
    }

    public void setSearchResults(List<LyricItemDTO> searchResults) {
        this.searchResults = searchResults;
    }
}


