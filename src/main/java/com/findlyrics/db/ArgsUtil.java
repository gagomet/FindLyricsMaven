package com.findlyrics.db;

/**
 * Created by Padonag on 15.08.2014.
 */
public class ArgsUtil {
    private static String parameters;

    private ArgsUtil() {
    }

    public static String getparameters() {
        return parameters;
    }

    public static void setparameters(String parameters) {
        ArgsUtil.parameters = parameters;
    }
}
