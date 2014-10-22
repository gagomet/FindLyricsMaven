package com.findlyrics.db.hibernate;

import com.findlyrics.db.model.Song;

import java.util.List;

/**
 * Created by Padonag on 21.10.2014.
 */
public interface IHibernateSongDAO {

    public List<Song> getSong(String lyrics);

    public boolean addSong(Song song);
}
