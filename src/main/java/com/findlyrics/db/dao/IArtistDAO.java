package com.findlyrics.db.dao;

import com.findlyrics.db.model.Artist;
import com.findlyrics.exceptions.DataConnectionException;

/**
 * Created by Padonag on 04.08.2014.
 */
public interface IArtistDAO {
    public Artist getArtist(Long id) throws DataConnectionException;
    public Long addArtist(Artist artist) throws DataConnectionException;
}
