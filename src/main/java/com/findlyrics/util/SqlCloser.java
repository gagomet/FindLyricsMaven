package com.findlyrics.util;

import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Padonag on 15.08.2014.
 */
public class SqlCloser {
    private static final Logger log = Logger.getLogger(SqlCloser.class);

    private SqlCloser() {
    }

    //TODO change signature method instead void to boolean!!!
    public static void closeSQL(ResultSet resultSet, PreparedStatement preparedStatement) {
        if (resultSet != null && preparedStatement != null) {
            try {
                resultSet.close();
                closePreparedStatement(preparedStatement);
            } catch (SQLException e) {
                e.printStackTrace();
                log.debug("Throwing exception", e);
            }

        }
    }

    public static void closePreparedStatement(PreparedStatement preparedStatement) {
        if (preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
