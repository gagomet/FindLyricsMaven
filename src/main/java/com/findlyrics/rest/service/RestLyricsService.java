package com.findlyrics.rest.service;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.findlyrics.db.model.Artist;
import com.findlyrics.db.model.Song;
import com.findlyrics.db.service.ILyricService;
import com.findlyrics.db.service.IServiceFactory;
import com.findlyrics.exceptions.DataConnectionException;
import com.findlyrics.rest.model.SongPojo;
import com.findlyrics.ui.model.LyricItemDTO;
import com.findlyrics.ui.model.LyricsDTO;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Padonag on 10.08.2014.
 */
public class RestLyricsService implements ILyricService {
    private static final Logger log = Logger.getLogger(RestLyricsService.class);
    private static final String REST_URL = "http://api.lyricsnmusic.com/songs?api_key=3699a6ba6f1ecdc9b9e208123fd382&lyrics=";
    private int numberOfRecords;
    private String query = null;

    private RestLyricsService() {
    }

    public static final IServiceFactory factory = new IServiceFactory() {
        @Override
        public ILyricService getInstance() {
            return new RestLyricsService();
        }
    };

    @Override
    public LyricsDTO getPartDTO(int page, int recordsPerPage) throws DataConnectionException {
        if (query == null) {
            return new LyricsDTO();
        }
        List<SongPojo> inputData = jsonToPojo(getJsonFromRest(query));
        List<SongPojo> partialData = inputData.subList(page * recordsPerPage, Math.min((page + 1) * recordsPerPage, inputData.size()));
        LyricsDTO dto = new LyricsDTO();
        List<LyricItemDTO> entries = pojoToLyricItemDtoList(partialData);
        dto.setSearchResults(entries);
        return dto;
    }

    public LyricsDTO getFullDto(String query) {
        if (query == null) {
            return new LyricsDTO();
        }
        List<SongPojo> inputData = jsonToPojo(getJsonFromRest(query));
        LyricsDTO dto = new LyricsDTO();
        List<LyricItemDTO> entries = pojoToLyricItemDtoList(inputData);
        dto.setSearchResults(entries);
        return dto;
    }

    @Override
    public LyricsDTO hibernateGetFullDto(String lyrics) {
        log.debug("Trying to use hibernate with REST-service");
        return new LyricsDTO();
    }

    @Override
    public int getNumberOfRecords() {
        return numberOfRecords;
    }

    @Override
    public void setQuery(String query) {
        this.query = query;
    }

    private String queryToHttp(String query) {
        return REST_URL + query.replace(" ", "%20");
    }

    private String getJsonFromRest(String query) {
        String fullUrl = queryToHttp(query);
        CloseableHttpClient httpclient = HttpClients.createDefault();
        String responseBody = null;
        try {
            HttpGet httpget = new HttpGet(fullUrl);
            System.out.println("Executing request " + httpget.getRequestLine());
            // Create a custom response handler
            ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

                public String handleResponse(final HttpResponse response) throws /*ClientProtocolException,*/ IOException {
                    int status = response.getStatusLine().getStatusCode();
                    if (status >= HttpStatus.SC_OK && status < HttpStatus.SC_MULTIPLE_CHOICES) {
                        HttpEntity entity = response.getEntity();
                        return entity != null ? EntityUtils.toString(entity) : null;
                    } else {
                        throw new ClientProtocolException("Unexpected response status: " + status);
                    }
                }

            };

            try {
                responseBody = httpclient.execute(httpget, responseHandler);
            } catch (IOException e) {
                e.printStackTrace();
                log.debug("Throwing exception", e);
            }
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
                log.debug("Throwing exception", e);
            }
        }
        return responseBody;
    }

    private List<SongPojo> jsonToPojo(String json) {
        List<SongPojo> result = new ArrayList<SongPojo>();
        try {
            result = new ObjectMapper().readValue(json, new TypeReference<ArrayList<SongPojo>>() {
            });

        } catch (IOException e) {
            e.printStackTrace();
            log.debug("Throwing exception", e);
        }
        numberOfRecords = result.size();
        return result;
    }

    private List<LyricItemDTO> pojoToLyricItemDtoList(List<SongPojo> pojoList) {
        List<LyricItemDTO> result = new LinkedList<LyricItemDTO>();
        for (SongPojo currentSong : pojoList) {
            Artist newArtist = new Artist(currentSong.getArtist().getName());
            Song newSong = new Song(currentSong.getTitle(), currentSong.getUrl());
            LyricItemDTO tempResult = new LyricItemDTO(newArtist, newSong);
            result.add(tempResult);
        }
        return result;
    }

}

