package com.findlyrics.util;

import com.findlyrics.db.dao.implementations.ArtistDAO;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Padonag on 15.08.2014.
 */
public class SqlCloserUtil {
    private static final Logger log = Logger.getLogger(ArtistDAO.class);
    private SqlCloserUtil() {
    }

    public static void closeSQL(ResultSet resultSet, PreparedStatement preparedStatement){
        if(resultSet !=null && preparedStatement!=null){
            try {
                resultSet.close();
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
                log.debug("Throwing exception", e);
            }

        }
    }

    public static void closePreparedStatement(PreparedStatement preparedStatement){
        if(preparedStatement!=null){
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
