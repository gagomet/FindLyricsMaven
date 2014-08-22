package com.findlyrics.exceptions;

/**
 * Created by Padonag on 21.08.2014.
 */
public class DbConnectionException extends Exception {

    public DbConnectionException(String errorMsg){
        super(errorMsg);
    }

}
