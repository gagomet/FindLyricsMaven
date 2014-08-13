package com.findlyrics.db;

import com.findlyrics.ui.MainForm;

/**
 * Created by Padonag on 21.07.2014.
 */
public class StartApp {

    public static final String[] args = {"/db.properties"};

    public static void main(String[] args) {
    MainForm guiForm = new MainForm("/db.properties");

    }
}



