package com.findlyrics.type;

/**
 * Created by Padonag on 02.09.2014.
 */
public enum ServiceType {

    DB("DbAccessService"),
    HTTP("HttpWebService"),
    REST("RestWebService");

    private final String name;

    private ServiceType(String arg) {
        name = arg;
    }

    public boolean equalsName(String otherName) {
        return (otherName == null) ? false : name.equals(otherName);
    }

    public String toString() {
        return name;
    }
}
