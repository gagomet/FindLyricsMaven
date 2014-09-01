package com.findlyrics.util;

import com.findlyrics.exceptions.DbConnectionException;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.log4j.Logger;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Padonag on 21.07.2014.
 */
public class ConnectionManager {
    private static final Logger log = Logger.getLogger(ConnectionManager.class);
    private static Connection connection;
    private ComboPooledDataSource c3p0Pool;

    private ConnectionManager() {
        try {
            this.c3p0Pool = new ComboPooledDataSource();
            c3p0Pool.setDriverClass(PropertiesManager.getProperty("db.driver"));
            c3p0Pool.setJdbcUrl(PropertiesManager.getProperty("db.url"));
            c3p0Pool.setUser(PropertiesManager.getProperty("db.login"));
            c3p0Pool.setPassword(PropertiesManager.getProperty("db.password"));
            connection = c3p0Pool.getConnection();

        } catch (SQLException e) {
            log.debug("Throwing exception", e);
            //            throw new DbConnectionException("Db connection is dead!");
        } catch (PropertyVetoException e) {
            log.debug("Throwing exception", e);
        }
    }

    private static class Holder {
        private static final ConnectionManager INSTANCE = new ConnectionManager();
    }

    public Connection getConnection() throws DbConnectionException {
        if (connection == null) {
            throw new DbConnectionException("Db connection is dead!");
        }
        return connection;
    }

    public static ConnectionManager getInstance() {
        return Holder.INSTANCE;
    }
}
