package com.findlyrics.http.service;

import com.findlyrics.db.model.Artist;
import com.findlyrics.db.model.Song;
import com.findlyrics.http.type.ForArguments;
import com.findlyrics.service.ILyricService;
import com.findlyrics.ui.model.LyricItemDTO;
import com.findlyrics.ui.model.LyricsDTO;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Padonag on 18.08.2014.
 */
public class HttpLyricsService implements ILyricService {

    private static final Logger log = Logger.getLogger(HttpLyricsService.class);
    private static final String SERVICE_URL = "http://webservices.lyrdb.com/lookup.php?q=";
    private static final String URL_TO_GET_LYRICS = "http://webservices.lyrdb.com/getlyr.php?q=";

    //TODO make methods private after testing!!!!

    @Override
    public LyricsDTO getDTO(String query) {
        LyricsDTO dto = new LyricsDTO();
        String response = getHttpResponse(getRequestUrl(query));
        dto.setSearchResults(parseResponse(response));
        return dto;
    }

    public String getRequestUrl(String query) {
        return SERVICE_URL + query + ForArguments.FOR_WORD_IN_LYRICS;
    }

    public String getLyricsUrl(String id) {
        return URL_TO_GET_LYRICS + id;
    }

    public String getHttpResponse(String request) {
        String response = "";
        try {
            URL requestUrl = new URL(request);
            URLConnection connection = requestUrl.openConnection();
            InputStream in = connection.getInputStream();
            response = IOUtils.toString(in, "UTF-8");

        } catch (MalformedURLException e) {
            e.printStackTrace();
            log.debug("Throwing exception", e);
        } catch (IOException e) {
            e.printStackTrace();
            log.debug("Throwing exception", e);
        }
        return response;
    }

    public List<LyricItemDTO> parseResponse(String response) {
        List<LyricItemDTO> result = new LinkedList<LyricItemDTO>();
        for (String currentEntity : response.split("\\r?\\n")) {
            String[] song = currentEntity.split("\\\\");
            String currentLyrics = getHttpResponse(getLyricsUrl(song[0]));
            Artist currentArtist = new Artist(song[2]);
            Song currentSong = new Song(song[1], currentLyrics);
            LyricItemDTO currentItem = new LyricItemDTO(currentArtist, currentSong);
            result.add(currentItem);
        }
        return result;
    }
}
