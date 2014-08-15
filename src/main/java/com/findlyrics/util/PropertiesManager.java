package com.findlyrics.util;

import org.apache.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

/**
 * Created by Padonag on 07.07.2014.
 */
public class PropertiesManager {
    private static final Logger log = Logger.getLogger(PropertiesManager.class);
    private static Properties properties = new Properties();
//    private static final String propertyFile = "/db.properties";
    private static volatile PropertiesManager instance;

    private PropertiesManager() {
        try {
            InputStream in = getClass().getResourceAsStream(ArgsUtil.getparameters());
            properties.load(in);
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            log.debug("Throwing exception", e);
        } catch (InvalidPropertiesFormatException e) {
            e.printStackTrace();
            log.debug("Throwing exception", e);
        } catch (IOException e) {
            e.printStackTrace();
            log.debug("Throwing exception", e);
        }
    }


    public static String getProperty(String propertyKey) {
        return properties.getProperty(propertyKey);
    }

    public static PropertiesManager getInstance() {
        if (instance == null) {
            synchronized (PropertiesManager.class) {
                if (instance == null)
                    instance = new PropertiesManager();
            }
        }
        return instance;
    }

}
