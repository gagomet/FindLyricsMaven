package com.findlyrics.db.service.implementetions;

import com.findlyrics.db.dao.implementations.ArtistDAO;
import com.findlyrics.db.dao.implementations.SongDAO;
import com.findlyrics.db.model.Artist;
import com.findlyrics.db.model.Song;
import com.findlyrics.db.service.ILyricsService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Padonag on 06.08.2014.
 */
public class LyricsService implements ILyricsService {
    private ArtistDAO artistDAO;
    private SongDAO songDAO;

    public LyricsService(ArtistDAO artistDAO, SongDAO songDAO) {
        this.artistDAO = artistDAO;
        this.songDAO = songDAO;
    }

    @Override
    public List<Artist> getArtist(String text) {
        Map<Long, Artist> resultMap = new HashMap<Long, Artist>();
        List<Song> songList = songDAO.getSongs(text);

        for(Song currentSong : songList){
            Long artistID = currentSong.getArtistId();
            Artist currentArtist = artistDAO.getArtist(artistID);
            if(resultMap.containsKey(artistID)){
               Artist temporary = resultMap.get(artistID);
                temporary.addSong(currentSong);
                resultMap.put(artistID, temporary);
            } else {
                currentArtist.addSong(currentSong);
                resultMap.put(artistID, currentArtist);
            }
        }
        return new ArrayList<Artist>(resultMap.values());
    }

    @Override
    public void addEntity(String artistName, String title, String text) {

    }
}
