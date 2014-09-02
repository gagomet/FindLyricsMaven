package main.java.com.findlyrics.ui.model;

import main.java.com.findlyrics.exceptions.DbConnectionException;
import main.java.com.findlyrics.db.service.ILyricService;


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

}
