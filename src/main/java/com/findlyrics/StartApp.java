package com.findlyrics;

import com.findlyrics.http.ForArguments;
import com.findlyrics.http.service.HttpLyricsService;
import com.findlyrics.ui.MainForm;
import com.findlyrics.ui.model.LyricsDTO;
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



