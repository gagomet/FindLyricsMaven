package main.java.com.findlyrics.db.dao;

import main.java.com.findlyrics.db.model.Song;
import main.java.com.findlyrics.exceptions.DbConnectionException;

import java.util.List;

/**
 * Created by Padonag on 04.08.2014.
 */
public interface ISongDAO {
    public List<Song> getSongs(String lyrics) throws DbConnectionException;

    public boolean addSong(Song song) throws DbConnectionException;
}
