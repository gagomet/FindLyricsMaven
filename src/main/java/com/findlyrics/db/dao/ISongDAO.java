package com.findlyrics.db.dao;

import com.findlyrics.db.model.Song;
import com.findlyrics.exceptions.DbConnectionException;

import java.util.List;

/**
 * Created by Padonag on 04.08.2014.
 */
public interface ISongDAO {
    public List<Song> getSongs(String lyrics) throws DbConnectionException;
    public void addSong(Song song) throws DbConnectionException;
}
