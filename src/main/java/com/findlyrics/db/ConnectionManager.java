package com.findlyrics.db;

import com.mchange.v2.c3p0.*;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Padonag on 21.07.2014.
 */
public class ConnectionManager {
    private static Connection connection;
    private ComboPooledDataSource c3p0Pool;

    public ConnectionManager(PropertiesManager propertiesManager){
        try {
            this.c3p0Pool  = new ComboPooledDataSource();
            c3p0Pool.setDriverClass(PropertiesManager.getProperty("db.driver"));
            c3p0Pool.setJdbcUrl(PropertiesManager.getProperty("db.url"));
            c3p0Pool.setUser(PropertiesManager.getProperty("db.login"));
            c3p0Pool.setPassword(PropertiesManager.getProperty("db.password"));
            connection = c3p0Pool.getConnection();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
    }


    public static Connection getConnection() {
        return connection;
    }
}
