package com.findlyrics.db.hibernate;

import com.findlyrics.db.model.Artist;

/**
 * Created by Padonag on 21.10.2014.
 */
public interface IHibernateArtistDAO {
    public Artist getArtist(Long id);

    public Long addArtist(Artist artist);
}
