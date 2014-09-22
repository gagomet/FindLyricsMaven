package com.findlyrics.db.dao;

import com.findlyrics.db.model.Song;
import com.findlyrics.exceptions.DataConnectionException;
import com.findlyrics.util.ConnectionManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Padonag on 11.09.2014.
 */
public class PartialSongDAO {
    private int noOfRecords;
    private String lyrics;

    public PartialSongDAO() {
    }

    public List<Song> getSongsPart(int offset, int noOfRecords) throws DataConnectionException {
        List<Song> list = new ArrayList<Song>();
        String query = "SELECT SQL_CALC_FOUND_ROWS * FROM songs WHERE songs.lyrics LIKE '%" + lyrics + "%' LIMIT "
                + offset + ", " + noOfRecords;
        Statement statement = null;
        ResultSet resultSet;
        try {
            Connection connection = ConnectionManager.getInstance().getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                Song song = new Song();
                song.setArtistId(resultSet.getLong("artist_id"));
                song.setTitle(resultSet.getString("song_name"));
                song.setLyrics(resultSet.getString("lyrics"));
                list.add(song);
            }
            resultSet.close();
            resultSet = statement.executeQuery("SELECT FOUND_ROWS()");
            if (resultSet.next())
                this.noOfRecords = resultSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null)
                    statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public int getNoOfRecords() {
        return noOfRecords;
    }

    public void setLyrics(String lyrics) {
        this.lyrics = lyrics;
    }
}
