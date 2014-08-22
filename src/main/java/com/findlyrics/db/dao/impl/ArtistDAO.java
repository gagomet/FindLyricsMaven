package com.findlyrics.db.dao.impl;

import com.findlyrics.exceptions.DbConnectionException;
import com.findlyrics.util.ConnectionManager;
import com.findlyrics.db.dao.IArtistDAO;
import com.findlyrics.db.model.Artist;
import com.findlyrics.util.SqlCloser;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * Created by Padonag on 04.08.2014.
 */
public class ArtistDAO implements IArtistDAO {

    private static final Logger log = Logger.getLogger(ArtistDAO.class);
    private static final String getArtistFromDBQuery = "SELECT * FROM artists WHERE artists.id = ?";
    private static final String addArtistToDBQuery = "INSERT INTO artists (name) VALUES (?)";


    public ArtistDAO() {

    }


    @Override
    public Artist getArtist(Long id) throws DbConnectionException{
        Artist artist = new Artist();
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = ConnectionManager.getInstance().getConnection().prepareStatement(getArtistFromDBQuery);
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            artist = parseResultSet(resultSet);
        } catch (SQLException e) {
            log.debug("Throwing exception", e);
        } finally {
            SqlCloser.closeResultSet(resultSet);
            SqlCloser.closePreparedStatement(preparedStatement);

        }
        return artist;
    }

    @Override
    public void addArtist(Artist artist) throws DbConnectionException{
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = ConnectionManager.getInstance().getConnection().prepareStatement(addArtistToDBQuery);
            preparedStatement.setString(1, artist.getName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.debug("Throwing exception", e);
        } finally {
            SqlCloser.closePreparedStatement(preparedStatement);
        }
    }

    private Artist parseResultSet(ResultSet resultSet) throws SQLException {
        Artist artist = new Artist();
        while (resultSet.next()) {
            artist = new Artist(resultSet.getString("artist_name"));

        }

        return artist;
    }
}
