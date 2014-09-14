package com.findlyrics.db.service.impl;

import com.findlyrics.db.service.ILyricService;
import com.findlyrics.http.service.HttpLyricsService;
import com.findlyrics.rest.service.RestLyricsService;
import com.findlyrics.type.ServiceType;

/**
 * Created by Padonag on 02.09.2014.
 */
public class LyricServiceFactory {
    public static ILyricService getService(ServiceType serviceType) {
        ILyricService service = null;
        switch (serviceType) {
            case DB:
                service = new DBLyricsService();
                break;
            case HTTP:
                service = new HttpLyricsService();
                break;
            case REST:
                service = new RestLyricsService();
                break;
        }
        return service;
    }
}
