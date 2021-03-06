package com.findlyrics.db.service;

import com.findlyrics.exceptions.DataConnectionException;
import com.findlyrics.ui.model.LyricsDTO;

/**
 * Created by Padonag on 17.08.2014.
 */
public interface ILyricService {

    public LyricsDTO getPartDTO(int page, int recordsPerPage) throws DataConnectionException;

    public int getNumberOfRecords();

    public void setQuery(String query);
}
