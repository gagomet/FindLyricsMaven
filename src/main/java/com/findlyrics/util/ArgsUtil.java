package com.findlyrics.util;

/**
 * Created by Padonag on 15.08.2014.
 */
public class ArgsUtil {
    private static String parameters;

    private ArgsUtil() {
    }

    public static String getParameters() {
        return parameters;
    }

    public static void setParameters(String parameters) {
        ArgsUtil.parameters = parameters;
    }
}
