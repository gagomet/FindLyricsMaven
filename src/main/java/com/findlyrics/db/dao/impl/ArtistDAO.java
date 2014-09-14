package com.findlyrics.db.dao.impl;

import com.findlyrics.db.dao.IArtistDAO;
import com.findlyrics.db.model.Artist;
import com.findlyrics.exceptions.DataConnectionException;
import com.findlyrics.util.ConnectionManager;
import com.findlyrics.util.SqlCloser;
import org.apache.log4j.Logger;

import java.sql.*;


/**
 * Created by Padonag on 04.08.2014.
 */
public class ArtistDAO implements IArtistDAO {

    private static final Logger log = Logger.getLogger(ArtistDAO.class);
    private static final String getArtistFromDBQuery = "SELECT * FROM artists WHERE artists.id = ?";
    private static final String addArtistToDBQuery = "INSERT INTO artists (artist_name) VALUES (?)";
    private static final String checkArtistNameInDB = "SELECT * FROM artists WHERE artists.artist_name = ?";

    public ArtistDAO() {

    }

    @Override
    public Artist getArtist(Long id) throws DataConnectionException {
        Artist artist = new Artist();
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = ConnectionManager.getInstance().getConnection().prepareStatement(getArtistFromDBQuery);
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            artist = parseResultSet(resultSet);
        } catch (SQLException e) {
            log.debug("Throwing exception ", e);
        } finally {
            SqlCloser.closeResultSet(resultSet);
            SqlCloser.closePreparedStatement(preparedStatement);
        }
        return artist;
    }

    @Override
    public Long addArtist(Artist artist) throws DataConnectionException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        try {
            Connection connection = ConnectionManager.getInstance().getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(addArtistToDBQuery, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, artist.getName());
            preparedStatement.executeUpdate();
            connection.commit();
            connection.setAutoCommit(true);
            log.info("Entry added to DB " + artist.toString());
            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                return resultSet.getLong(1);
            }
        } catch (SQLException e) {
            log.debug("Throwing exception ", e);

            return null;
        } finally {
            SqlCloser.closePreparedStatement(preparedStatement);


        }
        return null;
    }

    private Artist parseResultSet(ResultSet resultSet) throws SQLException {
        Artist artist = new Artist();
        while (resultSet.next()) {
            artist = new Artist(resultSet.getString("artist_name"));
        }

        return artist;
    }

    public Long isArtistExistInDB(String artistName) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = ConnectionManager.getInstance().getConnection().prepareStatement(checkArtistNameInDB);
            preparedStatement.setString(1, artistName);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getLong("id");
            }
        } catch (SQLException e) {
            log.debug("Throwing exception ", e);
        } catch (DataConnectionException e) {
            log.debug("Throwing exception ", e);
        } finally {
            SqlCloser.closeResultSet(resultSet);
            SqlCloser.closePreparedStatement(preparedStatement);
        }
        return null;
    }
}
