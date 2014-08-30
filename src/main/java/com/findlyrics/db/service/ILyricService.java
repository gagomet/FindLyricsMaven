package com.findlyrics.db.service;

import com.findlyrics.exceptions.DbConnectionException;
import com.findlyrics.ui.model.LyricsDTO;

/**
 * Created by Padonag on 17.08.2014.
 */
public interface ILyricService {
    public LyricsDTO getDTO(String query) throws DbConnectionException;
}
