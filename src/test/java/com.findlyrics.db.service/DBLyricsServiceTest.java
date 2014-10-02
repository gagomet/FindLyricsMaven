package com.findlyrics.db.service;

import com.findlyrics.db.service.impl.DBLyricsService;
import com.findlyrics.exceptions.DataConnectionException;
import org.junit.Test;

/**
 * Created by Oleksandr_Kramskyi on 9/1/2014.
 */
public class DBLyricsServiceTest {

    @Test
    public void findSong() throws DataConnectionException {

        System.out.println("test is running...");
        ILyricService dbService = DBLyricsService.factory.getInstance();

//        IArtistDAO dao = new ArtistDAO();
//        Artist artist = dao.getArtist(1L);
//        Assert.assertEquals("queen", artist.getName());
    }
}
