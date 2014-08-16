package com.findlyrics;

import com.findlyrics.ui.MainForm;
import com.findlyrics.util.ArgsUtil;

/**
 * Created by Padonag on 21.07.2014.
 */
public class StartApp {


    public static void main(String[] args) {
    ArgsUtil.setParameters(args[0]);
    MainForm guiForm = new MainForm();

    }
}



