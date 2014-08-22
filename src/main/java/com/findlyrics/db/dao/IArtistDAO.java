package com.findlyrics.db.dao;

import com.findlyrics.db.model.Artist;
import com.findlyrics.exceptions.DbConnectionException;

/**
 * Created by Padonag on 04.08.2014.
 */
public interface IArtistDAO {
    public Artist getArtist(Long id) throws DbConnectionException;
    public void addArtist(Artist artist) throws DbConnectionException;
}
