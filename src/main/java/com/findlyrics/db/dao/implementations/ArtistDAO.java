package com.findlyrics.db.dao.implementations;

import com.findlyrics.db.ConnectionManager;
import com.findlyrics.db.dao.IArtistDAO;
import com.findlyrics.db.model.Artist;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Padonag on 04.08.2014.
 */
public class ArtistDAO implements IArtistDAO {
    private ConnectionManager connectionManager;
    private SongDAO songDAO;
    public static final String getArtistFromDBQuery = "SELECT * FROM artists WHERE artists.id = ?";
    public static final String addArtistToDBQuery = "INSERT INTO artists (name) VALUES (?)";

    public ArtistDAO(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }


    @Override
    public Artist getArtist(Long id) {
        Artist artist = new Artist();
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connectionManager.getConnection().prepareStatement(getArtistFromDBQuery);
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            artist = parseResultSet(resultSet);
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
        return artist;
    }

    @Override
    public void addArtist(Artist artist) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connectionManager.getConnection().prepareStatement(addArtistToDBQuery);
            preparedStatement.setString(1, artist.getName());
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

    private Artist parseResultSet(ResultSet resultSet) throws SQLException {
        Artist artist = new Artist();
        while (resultSet.next()) {
            artist = new Artist(resultSet.getString("ArtistName"));
        }

        return artist;

    }


}
