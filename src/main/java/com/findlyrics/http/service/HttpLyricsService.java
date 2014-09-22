package com.findlyrics.http.service;

import com.findlyrics.db.model.Artist;
import com.findlyrics.db.model.Song;
import com.findlyrics.db.service.ILyricService;
import com.findlyrics.exceptions.DataConnectionException;
import com.findlyrics.type.ForArguments;
import com.findlyrics.ui.model.LyricItemDTO;
import com.findlyrics.ui.model.LyricsDTO;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
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
    private static final String EMPTY_STRING = "";
    private int entities;
    private String query = null;


    @Override
    public LyricsDTO getDTO(String query) throws DataConnectionException {
        LyricsDTO dto = new LyricsDTO();
        String response = null;
        response = getHttpResponse(SERVICE_URL + query + ForArguments.FOR_WORD_IN_LYRICS);
        dto.setSearchResults(parseResponse(response));
        return dto;
    }

    @Override
    public LyricsDTO getPartDTO(int page, int recordsPerPage) throws DataConnectionException {
        if (query == null) {
            return null;
        }
        LyricsDTO dto = new LyricsDTO();
        String response = null;
        response = getHttpResponse(SERVICE_URL + query + ForArguments.FOR_WORD_IN_LYRICS);
        List<LyricItemDTO> inputData = parseResponse(response);
        List<LyricItemDTO> partialData = inputData.subList(page * recordsPerPage, Math.min((page + 1) * recordsPerPage, inputData.size()));
        dto.setSearchResults(partialData);
        return dto;
    }

    @Override
    public int getNumberOfRecords() {
        return entities;
    }

    @Override
    public void setQuery(String query) {
        this.query = query;
    }

    private String getHttpResponse(String request) throws DataConnectionException {
        String response = "";
        try {
            URL requestUrl = new URL(request);
            URLConnection connection = requestUrl.openConnection();
            InputStream in = connection.getInputStream();
            response = IOUtils.toString(in, "UTF-8");

        } catch (ConnectException e) {
            throw new DataConnectionException("No connection with http service");
        } catch (MalformedURLException e) {
            e.printStackTrace();
            log.debug("Throwing exception", e);
        } catch (IOException e) {
            e.printStackTrace();
            log.debug("Throwing exception", e);
        }
        return response;
    }

    private List<LyricItemDTO> parseResponse(String response) throws DataConnectionException {
        List<LyricItemDTO> result = new LinkedList<LyricItemDTO>();
        for (String currentEntity : response.split("\\r?\\n")) {
            String[] song = currentEntity.split("\\\\");
            String currentLyrics = trimLyricsResponse(getHttpResponse(URL_TO_GET_LYRICS + song[0]));
            Artist currentArtist = new Artist(song[2]);
            Song currentSong = new Song(song[1], currentLyrics);
            LyricItemDTO currentItem = new LyricItemDTO(currentArtist, currentSong);
            result.add(currentItem);
        }
        entities = result.size();
        return result;
    }

    private String trimLyricsResponse(String response) {
        String[] trimmedLyrics = response.split("<br />");
        return trimmedLyrics[2];
    }
}
