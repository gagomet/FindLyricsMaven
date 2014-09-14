package com.findlyrics.db.dao;

import com.findlyrics.db.model.Song;
import com.findlyrics.exceptions.DataConnectionException;

import java.util.List;

/**
 * Created by Padonag on 04.08.2014.
 */
public interface ISongDAO {
    public List<Song> getSongs(String lyrics) throws DataConnectionException;

    public boolean addSong(Song song) throws DataConnectionException;
}
