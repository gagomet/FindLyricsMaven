package com.findlyrics.ui.model;


import com.findlyrics.service.ILyricService;
import com.findlyrics.ui.OutputTableModel;

/**
 * Created by Padonag on 19.08.2014.
 */
public class UiModel {
    private LyricsDTO dto;
    private OutputTableModel outputTableModel;


    public LyricsDTO getDto() {
        return dto;
    }

    public void createTableModel(ILyricService service, String query){
        dto = service.getDTO(query);
        outputTableModel = new OutputTableModel(dto);
    }


    public OutputTableModel getOutputTableModel() {
        return outputTableModel;
    }

}
