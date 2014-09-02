package com.findlyrics.db.service;

import main.java.com.findlyrics.db.dao.IArtistDAO;
import main.java.com.findlyrics.db.dao.impl.ArtistDAO;
import main.java.com.findlyrics.db.model.Artist;
import main.java.com.findlyrics.exceptions.DbConnectionException;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Oleksandr_Kramskyi on 9/1/2014.
 */
public class DBLyricsServiceTest {

    @Test
    public void findSong() throws DbConnectionException {

        System.out.println("test is running...");
//        IArtistDAO dao = new ArtistDAO();
//        Artist artist = dao.getArtist(1L);
//        Assert.assertEquals("queen", artist.getName());
    }
}
