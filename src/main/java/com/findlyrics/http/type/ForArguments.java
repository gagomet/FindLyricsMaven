package com.findlyrics.http.type;

/**
 * Created by Padonag on 18.08.2014.
 */
public enum ForArguments {

    FOR_ARTIST("&for=artist"),
    FOR_TRACKNAME("&for=trackname"),
    FOR_WORD_IN_LYRICS("&for=inlyrics");

    private final String name;

    private ForArguments(String arg){
        name = arg;
    }

    public boolean equalsName(String otherName){
        return (otherName == null)? false:name.equals(otherName);
    }

    public String toString(){
        return name;
    }
}
