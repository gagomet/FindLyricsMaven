package com.findlyrics.db.service.impl;

import com.findlyrics.db.dao.impl.ArtistDAO;
import com.findlyrics.db.dao.impl.SongDAO;
import com.findlyrics.db.model.Artist;
import com.findlyrics.db.model.Song;
import com.findlyrics.db.service.ILyricService;
import com.findlyrics.exceptions.DbConnectionException;
import com.findlyrics.ui.model.LyricItemDTO;
import com.findlyrics.ui.model.LyricsDTO;
import org.apache.log4j.Logger;

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

    public DBLyricsService() {
        this.artistDAO = new ArtistDAO();
        this.songDAO = new SongDAO();
    }

    public LyricsDTO getDTO(String query) throws DbConnectionException {
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

    public boolean addSongToDB(LyricItemDTO dto) throws DbConnectionException {
        Song song = new Song(dto.getSongName(), dto.getLyrics());
        Long artistID = artistDAO.isArtistExistInDB(dto.getArtistName());
        if (artistID == null) {
            Artist artist = new Artist(dto.getArtistName());
            Long newArtistId = artistDAO.addArtist(artist);
            song.setArtistId(newArtistId);
            artist.addSong(song);
            artistDAO.addArtist(artist);
            log.info("Entry added to DB " + artist.toString());
            log.info("Entry added to DB " + song.toString());
            return true;
        } else {
            if(songDAO.isSongAlreadyInDB(song)){
                return false;
            }
            song.setArtistId(artistID);
            songDAO.addSong(song);
            log.info("Entry added to DB " + song.toString() + " into repertoir of existing artist with ID " + artistID);
            return true;
        }


    }


    private List<Artist> getArtist(String text) throws DbConnectionException {
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
