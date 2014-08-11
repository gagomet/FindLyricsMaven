package com.findlyrics.db.dao.implementations;

import com.findlyrics.db.ConnectionManager;
import com.findlyrics.db.dao.ISongDAO;
import com.findlyrics.db.model.Song;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Padonag on 04.08.2014.
 */
public class SongDAO implements ISongDAO {
    private ConnectionManager connectionManager;
    public static final String getSongsFromDBQuery = "SELECT * FROM songs WHERE songs.lyrics LIKE ?";
    public static final String addSongsToDBQuery = "INSERT INTO songs(idArtist, SongName, Lyrics) VALUES(?, ?, ?)";

    public SongDAO(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    @Override
    public List<Song> getSongs(String lyrics) {
        List<Song> result = new ArrayList<Song>();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connectionManager.getConnection().prepareStatement(getSongsFromDBQuery);
            preparedStatement.setString(1, "%"+lyrics+"%");
            resultSet = preparedStatement.executeQuery();
            result = parseResultSet(resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                resultSet.close();
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        return result;
    }

    @Override
    public void addSong(Song song) {
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connectionManager.getConnection().prepareStatement(addSongsToDBQuery);
            preparedStatement.setLong(1, song.getArtistId());
            preparedStatement.setString(2, song.getTitle());
            preparedStatement.setString(3, song.getLyrics());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


    }

    private List<Song> parseResultSet(ResultSet resultSet) throws SQLException {
        List<Song> result = new ArrayList<Song>();
        if(resultSet == null){
            return Collections.EMPTY_LIST;
        }
        while(resultSet.next()){
            Song currentSong = new Song(resultSet.getString("SongName"), resultSet.getString("Lyrics"));
            currentSong.setArtistId(resultSet.getLong("idArtist"));
            currentSong.setId(resultSet.getLong("idSongs"));
            result.add(currentSong);
        }
        return result;
    }
}
