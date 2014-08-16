package com.findlyrics.util;

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

    private ComboPooledDataSource c3p0Pool;

    private ConnectionManager() {
        try {
            c3p0Pool = new ComboPooledDataSource();
            c3p0Pool.setDriverClass(PropertiesManager.getProperty("db.driver"));
            c3p0Pool.setJdbcUrl(PropertiesManager.getProperty("db.url"));
            c3p0Pool.setUser(PropertiesManager.getProperty("db.login"));
            c3p0Pool.setPassword(PropertiesManager.getProperty("db.password"));
        } catch (PropertyVetoException e) {
            e.printStackTrace();
            log.debug("Throwing exception", e);
        }
    }

    private static class Holder {
        private static final ConnectionManager INSTANCE = new ConnectionManager();
    }


    public static Connection getConnection() throws SQLException {
        return Holder.INSTANCE.c3p0Pool.getConnection();
    }

}
