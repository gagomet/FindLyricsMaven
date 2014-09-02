package main.java.com.findlyrics.db.dao;

import main.java.com.findlyrics.db.model.Artist;
import main.java.com.findlyrics.exceptions.DbConnectionException;

/**
 * Created by Padonag on 04.08.2014.
 */
public interface IArtistDAO {
    public Artist getArtist(Long id) throws DbConnectionException;

    public Long addArtist(Artist artist) throws DbConnectionException;
}
