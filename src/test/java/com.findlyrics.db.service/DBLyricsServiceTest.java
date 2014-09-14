package com.findlyrics.db.service;

import com.findlyrics.db.service.impl.LyricServiceFactory;
import com.findlyrics.exceptions.DataConnectionException;
import com.findlyrics.type.ServiceType;
import org.junit.Test;

/**
 * Created by Oleksandr_Kramskyi on 9/1/2014.
 */
public class DBLyricsServiceTest {

    @Test
    public void findSong() throws DataConnectionException {

        System.out.println("test is running...");
        ILyricService dbService = LyricServiceFactory.getService(ServiceType.DB);

//        IArtistDAO dao = new ArtistDAO();
//        Artist artist = dao.getArtist(1L);
//        Assert.assertEquals("queen", artist.getName());
    }
}
