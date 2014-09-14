package com.findlyrics.db.service;

import com.findlyrics.exceptions.DataConnectionException;
import com.findlyrics.ui.model.LyricsDTO;

import java.net.ConnectException;

/**
 * Created by Padonag on 17.08.2014.
 */
public interface ILyricService {
    public LyricsDTO getDTO(String query) throws DataConnectionException;
}
