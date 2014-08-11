package com.findlyrics.db.dao;

import com.findlyrics.db.model.Artist;

/**
 * Created by Padonag on 04.08.2014.
 */
public interface IArtistDAO {
    public Artist getArtist(Long id);
    public void addArtist(Artist artist);
}
