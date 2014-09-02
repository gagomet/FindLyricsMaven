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


    public static boolean closeResultSet(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
                log.debug("Throwing exception", e);
                return false;
            }

        }
        return true;
    }

    public static boolean closePreparedStatement(PreparedStatement preparedStatement) {
        if (preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
                log.debug("Throwing exception", e);
                return false;
            }
        }
        return true;
    }

}
