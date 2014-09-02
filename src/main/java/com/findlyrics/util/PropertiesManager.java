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
    private static Properties properties;

    private PropertiesManager() {
    }

    public static String getProperty(String propertyKey) {
        if (properties == null) {
            properties = new Properties();
            try {
                InputStream in = PropertiesManager.class.getResourceAsStream(ArgsUtil.getParameters());
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

        return properties.getProperty(propertyKey);
    }
}
