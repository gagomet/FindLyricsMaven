package com.findlyrics.db.dao.impl;

import com.findlyrics.exceptions.DbConnectionException;
import com.findlyrics.util.ConnectionManager;
import com.findlyrics.db.dao.ISongDAO;
import com.findlyrics.db.model.Song;
import com.findlyrics.util.SqlCloser;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Padonag on 04.08.2014.
 */
public class SongDAO implements ISongDAO {

    private static final Logger log = Logger.getLogger(SongDAO.class);
    private static final String getSongsFromDBQuery = "SELECT * FROM songs WHERE songs.lyrics LIKE ?";
    private static final String addSongsToDBQuery = "INSERT INTO songs(artist_id, song_name, lyrics) VALUES(?, ?, ?)";

    public SongDAO() {

    }

    @Override
    public List<Song> getSongs(String lyrics) throws DbConnectionException {
        List<Song> result = new ArrayList<Song>();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = ConnectionManager.getInstance().getConnection().prepareStatement(getSongsFromDBQuery);
            preparedStatement.setString(1, "%" + lyrics + "%");
            resultSet = preparedStatement.executeQuery();
            result = parseResultSet(resultSet);

        } catch (SQLException e) {
           log.debug("Throwing exception", e);
        } finally {
            SqlCloser.closeResultSet(resultSet);
            SqlCloser.closePreparedStatement(preparedStatement);

        }
        log.info("Creating List<Song> object" + result.toString());
        return result;
    }

    @Override
    public void addSong(Song song) throws DbConnectionException{
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = ConnectionManager.getInstance().getConnection().prepareStatement(addSongsToDBQuery);
            preparedStatement.setLong(1, song.getArtistId());
            preparedStatement.setString(2, song.getTitle());
            preparedStatement.setString(3, song.getLyrics());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.debug("Throwing exception", e);
        } finally {
            SqlCloser.closePreparedStatement(preparedStatement);
        }
    }

    private List<Song> parseResultSet(ResultSet resultSet) throws SQLException {
        if (resultSet == null) {
            return Collections.EMPTY_LIST;
        }

        List<Song> result = new LinkedList<Song>();
        while (resultSet.next()) {
            Song currentSong = new Song(resultSet.getString("song_name"), resultSet.getString("lyrics"));
            currentSong.setArtistId(resultSet.getLong("artist_id"));
            currentSong.setId(resultSet.getLong("id"));
            result.add(currentSong);
        }
        return result;
    }
}
