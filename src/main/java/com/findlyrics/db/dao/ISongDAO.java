package com.findlyrics.db.dao;

import com.findlyrics.db.model.Song;

import java.util.List;

/**
 * Created by Padonag on 04.08.2014.
 */
public interface ISongDAO {
    public List<Song> getSongs(String lyrics);
    public void addSong(Song song);
}
