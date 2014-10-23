package com.findlyrics.rest.service;


import com.findlyrics.db.service.ILyricService;
import com.findlyrics.db.service.IServiceFactory;
import com.findlyrics.exceptions.DataConnectionException;
import com.findlyrics.ui.model.LyricItemDTO;
import com.findlyrics.ui.model.LyricsDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Padonag on 22.10.2014.
 */
public class ProxyRestLyricsService implements ILyricService {

    private static int NUMBER_OF_STUBS_RESULTS = 10;
    private RestLyricsService restLyricsService;

    private ProxyRestLyricsService() {
        this.restLyricsService = (RestLyricsService) RestLyricsService.factory.getInstance();
    }

    public static final IServiceFactory factory = new IServiceFactory() {
        @Override
        public ILyricService getInstance() {
            return new ProxyRestLyricsService();
        }
    };

    @Override
    public LyricsDTO getPartDTO(int page, int recordsPerPage) throws DataConnectionException {
        return restLyricsService.getPartDTO(page, recordsPerPage);
    }

    public LyricsDTO getFullDto(String query) {
        LyricsDTO result;
        try {
            result = restLyricsService.getFullDto(query);
        } catch (Exception e) {
            return stubResults();
        }
        return result;
    }

    @Override
    public LyricsDTO hibernateGetFullDto(String lyrics) {
        return restLyricsService.hibernateGetFullDto(lyrics);
    }

    @Override
    public int getNumberOfRecords() {
        return restLyricsService.getNumberOfRecords();
    }

    @Override
    public void setQuery(String query) {
        restLyricsService.setQuery(query);
    }

    private LyricsDTO stubResults() {
        LyricsDTO stubDto = new LyricsDTO();
        List<LyricItemDTO> stubList = new ArrayList<LyricItemDTO>();
        for (int i = 0; i < NUMBER_OF_STUBS_RESULTS; i++) {
            LyricItemDTO stubLyricItemDto = new LyricItemDTO("artist no " + i, "song name " + i, "lyrics la-la-la");
            stubList.add(stubLyricItemDto);
        }
        stubDto.setSearchResults(stubList);
        return stubDto;
    }
}
