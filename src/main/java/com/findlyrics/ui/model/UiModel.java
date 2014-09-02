package com.findlyrics.ui.model;

import com.findlyrics.db.service.ILyricService;
import com.findlyrics.exceptions.DbConnectionException;


/**
 * Created by Padonag on 19.08.2014.
 */
public class UiModel {
    private LyricsDTO dto;
    private OutputTableModel outputTableModel;

    public void createTableModel(ILyricService service, String query) throws DbConnectionException {
        dto = service.getDTO(query);
        outputTableModel = new OutputTableModel(dto);
    }

    public OutputTableModel getOutputTableModel() {
        return outputTableModel;
    }

    public void clearOutputTableModel() {
        outputTableModel = new OutputTableModel();
    }

}