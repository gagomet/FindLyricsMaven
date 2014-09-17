package com.findlyrics.ui.model;

import com.findlyrics.db.dao.BlindService;
import com.findlyrics.db.service.ILyricService;
import com.findlyrics.db.service.impl.DBLyricsService;
import com.findlyrics.exceptions.DataConnectionException;


/**
 * Created by Padonag on 19.08.2014.
 */
public class UiModel {
    private static final int NO_ON_PAGE = 20;
    private int offset;

    private LyricsDTO dto;
    private OutputTableModel outputTableModel;
    private PartialTableModel partialTableModel;

    public UiModel() {

    }

    public void createTableModel(ILyricService service, String query) throws DataConnectionException {
        dto = service.getDTO(query);
        outputTableModel = new OutputTableModel(dto);
    }

    public void createPartialTableModel(DBLyricsService service) throws DataConnectionException {
        partialTableModel = new PartialTableModel(service);

    }

    public OutputTableModel getOutputTableModel() {
        return outputTableModel;
    }

    public PartialTableModel getPartialTableModel() {
        return partialTableModel;
    }

    public ITableModelPagination getTableModel(){
        if (partialTableModel == null){
            return outputTableModel;
        }
        return  partialTableModel;
    }

    public void clearOutputTableModel() {
        outputTableModel = null;
    }

    public void clearPartialTableModel() { partialTableModel = null; }

}
