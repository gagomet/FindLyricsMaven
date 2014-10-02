package com.findlyrics.db.service.impl;

import com.findlyrics.db.dao.PartialSongDAO;
import com.findlyrics.db.dao.impl.ArtistDAO;
import com.findlyrics.db.dao.impl.SongDAO;
import com.findlyrics.db.model.Artist;
import com.findlyrics.db.model.Song;
import com.findlyrics.db.service.ILyricService;
import com.findlyrics.db.service.IServiceFactory;
import com.findlyrics.exceptions.DataConnectionException;
import com.findlyrics.ui.model.LyricItemDTO;
import com.findlyrics.ui.model.LyricsDTO;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Padonag on 06.08.2014.
 */
public class DBLyricsService implements ILyricService {
    private static final Logger log = Logger.getLogger(ArtistDAO.class);
    private ArtistDAO artistDAO;
    private SongDAO songDAO;
    private PartialSongDAO partialSongDAO;

    private DBLyricsService() {
        this.artistDAO = new ArtistDAO();
        this.songDAO = new SongDAO();
        this.partialSongDAO = new PartialSongDAO();
    }

    public static final IServiceFactory factory = new IServiceFactory() {
        @Override
        public ILyricService getInstance() {
            return new DBLyricsService();
        }
    };


    public LyricsDTO getDTO(String query) throws DataConnectionException {
        LyricsDTO dto = new LyricsDTO();
        List<Artist> inputData = getArtist(query);
        List<LyricItemDTO> lyricItemDTOs = new LinkedList<LyricItemDTO>();
        for (Artist currentArtist : inputData) {
            for (Song currentSong : currentArtist.getRepertoir()) {
                LyricItemDTO tempResult = new LyricItemDTO(currentArtist, currentSong);
                lyricItemDTOs.add(tempResult);
            }
        }
        dto.setSearchResults(lyricItemDTOs);
        return dto;
    }

    public LyricsDTO getPartDTO(int page, int recordsPerPage) throws DataConnectionException {
        LyricsDTO dto = new LyricsDTO();
        List<LyricItemDTO> lyricItemDTOs = new LinkedList<LyricItemDTO>();
        List<Song> list = partialSongDAO.getSongsPart(page * recordsPerPage,
                recordsPerPage);
        for (Song currentSong : list) {
            Artist tempArtist = artistDAO.getArtist(currentSong.getArtistId());
            LyricItemDTO itemDTO = new LyricItemDTO(tempArtist, currentSong);
            lyricItemDTOs.add(itemDTO);
        }
        dto.setSearchResults(lyricItemDTOs);
        return dto;
    }

    public boolean addSongToDB(LyricItemDTO dto) throws DataConnectionException {
        Song song = new Song(dto.getSongName(), dto.getLyrics());
        Long artistID = artistDAO.isArtistExistInDB(dto.getArtistName());
        if (artistID == null) {
            Artist artist = new Artist(dto.getArtistName());
            Long newArtistId = artistDAO.addArtist(artist);
            song.setArtistId(newArtistId);
            artist.addSong(song);
            songDAO.addSong(song);
            return true;
        } else {
            if (songDAO.isSongAlreadyInDB(song)) {
                return false;
            }
            song.setArtistId(artistID);
            songDAO.addSong(song);
            log.info("Entry added to DB " + song.toString() + " into repertoir of existing artist with ID " + artistID);
            return true;
        }
    }

    public String getLyricsFromUrl(String url) throws IOException {
        Document document = Jsoup.connect(url).get();
        Element lyrics = document.select("pre").get(0);
        return lyrics.text();
    }

    public PartialSongDAO getPartialSongDAO() {
        return partialSongDAO;
    }

    public void setQuery(String query) {
        partialSongDAO.setLyrics(query);
    }

    public int getNumberOfRecords() {
        System.out.println(partialSongDAO.getNoOfRecords());
        return partialSongDAO.getNoOfRecords();
    }

    private List<Artist> getArtist(String text) throws DataConnectionException {
        Map<Long, Artist> resultMap = new HashMap<Long, Artist>();
        List<Song> songList = songDAO.getSongs(text);

        for (Song currentSong : songList) {
            Long artistID = currentSong.getArtistId();
            Artist currentArtist = artistDAO.getArtist(artistID);
            if (resultMap.containsKey(artistID)) {
                Artist temporary = resultMap.get(artistID);
                temporary.addSong(currentSong);
                resultMap.put(artistID, temporary);
            } else {
                currentArtist.addSong(currentSong);
                resultMap.put(artistID, currentArtist);
            }
        }
        return new LinkedList<Artist>(resultMap.values());
    }

}
