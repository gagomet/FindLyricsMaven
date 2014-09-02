package main.java.com.findlyrics.db.service;

import main.java.com.findlyrics.exceptions.DbConnectionException;
import main.java.com.findlyrics.ui.model.LyricsDTO;

/**
 * Created by Padonag on 17.08.2014.
 */
public interface ILyricService {
    public LyricsDTO getDTO(String query) throws DbConnectionException;
}
