package com.findlyrics.service;

import com.findlyrics.ui.model.LyricsDTO;

/**
 * Created by Padonag on 17.08.2014.
 */
public interface ILyricService {
    public LyricsDTO getDTO(String query);
}
