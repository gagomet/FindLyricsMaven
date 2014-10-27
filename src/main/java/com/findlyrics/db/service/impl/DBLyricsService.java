package com.findlyrics.db.service.impl;

import com.findlyrics.db.dao.PartialSongDAO;
import com.findlyrics.db.dao.impl.ArtistDAO;
import com.findlyrics.db.dao.impl.SongDAO;
import com.findlyrics.db.hibernate.impl.HibernateArtistDAO;
import com.findlyrics.db.hibernate.impl.HibernateSongDAO;
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
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Padonag on 06.08.2014.
 */
public class DBLyricsService implements ILyricService {
    private static final Logger log = Logger.getLogger(ArtistDAO.class);
    private ArtistDAO artistDAO;
    private SongDAO songDAO;
    private PartialSongDAO partialSongDAO;
    private HibernateArtistDAO hibernateArtistDAO;
    private HibernateSongDAO hibernateSongDAO;

    private DBLyricsService() {
        this.artistDAO = new ArtistDAO();
        this.songDAO = new SongDAO();
        this.partialSongDAO = new PartialSongDAO();
        this.hibernateArtistDAO = new HibernateArtistDAO();
        this.hibernateSongDAO = new HibernateSongDAO();
    }

    public static final IServiceFactory factory = new IServiceFactory() {
        @Override
        public ILyricService getInstance() {
            return new DBLyricsService();
        }
    };

    @Override
    public LyricsDTO getPartDTO(int page, int recordsPerPage) throws DataConnectionException {
        LyricsDTO dto = new LyricsDTO();
        List<Song> list = partialSongDAO.getSongsPart(page * recordsPerPage,
                recordsPerPage);
        List<LyricItemDTO> lyricItemDTOs = mapArtistToSongs(list);
        dto.setSearchResults(lyricItemDTOs);
        return dto;
    }

    @Override
    public LyricsDTO getFullDto(String lyrics) throws DataConnectionException {
        LyricsDTO dto = new LyricsDTO();
        List<Song> songsList = songDAO.getSongs(lyrics);
        dto.setSearchResults(mapArtistToSongs(songsList));
        return dto;
    }

    @Override
    public LyricsDTO hibernateGetFullDto(String lyrics) throws DataConnectionException {
        LyricsDTO dto = new LyricsDTO();
        List<Song> songsList = hibernateSongDAO.getSongs(lyrics);
        dto.setSearchResults(hibernateMapArtistsToSongs(songsList));
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

    public boolean hibernateAddSongToDB(LyricItemDTO dto) throws DataConnectionException {
        Song song = new Song(dto.getSongName(), dto.getLyrics());
        Long artistID = hibernateArtistDAO.isArtistExistInDB(dto.getArtistName());
        if (artistID == null) {
            Artist artist = new Artist(dto.getArtistName());
            Long newArtistId = hibernateArtistDAO.addArtist(artist);
            song.setArtistId(newArtistId);
            artist.addSong(song);
            hibernateSongDAO.addSong(song);
            return true;
        } else {
            song.setArtistId(artistID);
            hibernateSongDAO.addSong(song);
            log.info("Entry added to DB " + song.toString() + " into repertoir of existing artist with ID " + artistID);
            return true;
        }
    }

    @Override
    public void setQuery(String query) {
        partialSongDAO.setLyrics(query);
    }

    @Override
    public int getNumberOfRecords() {
        System.out.println(partialSongDAO.getNoOfRecords());
        return partialSongDAO.getNoOfRecords();
    }

    private List<LyricItemDTO> mapArtistToSongs(List<Song> songList) throws DataConnectionException {
        List<LyricItemDTO> result = new LinkedList<LyricItemDTO>();
        for (Song currentSong : songList) {
            Artist tempArtist = artistDAO.getArtist(currentSong.getArtistId());
            LyricItemDTO itemDTO = new LyricItemDTO(tempArtist, currentSong);
            result.add(itemDTO);
        }
        return result;
    }

    private List<LyricItemDTO> hibernateMapArtistsToSongs(List<Song> songsPojoList) throws DataConnectionException {
        List<LyricItemDTO> result = new LinkedList<LyricItemDTO>();

        for (Song currentSong : songsPojoList) {
            Artist tempArtist = hibernateArtistDAO.getArtist(currentSong.getArtistId());
            LyricItemDTO itemDTO = new LyricItemDTO(tempArtist, currentSong);
            result.add(itemDTO);
        }
        return result;
    }

    private String getLyricsFromUrl(String url) throws IOException {
        Document document = Jsoup.connect(url).get();
        Element lyrics = document.select("pre").get(0);
        return lyrics.text();
    }


}
