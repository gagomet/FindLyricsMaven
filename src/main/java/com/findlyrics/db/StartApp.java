package com.findlyrics.db;

import com.findlyrics.ui.MainForm;
import com.findlyrics.util.ArgsUtil;

/**
 * Created by Padonag on 21.07.2014.
 */
public class StartApp {


    public static void main(String[] args) {
    ArgsUtil.setparameters(args[0]);
    MainForm guiForm = new MainForm();

    }
}



