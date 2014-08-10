package com.findlyrics.db.service;

import com.findlyrics.db.model.Artist;

import java.util.List;

/**
 * Created by Padonag on 06.08.2014.
 */
public interface ILyricsService {

    public List<Artist> getArtist(String text);
    public void addEntity(String artistName, String title, String text);
}
