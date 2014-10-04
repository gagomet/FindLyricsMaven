package com.findlyrics.ui.model;

import com.findlyrics.db.service.ILyricService;
import com.findlyrics.exceptions.DataConnectionException;
import com.findlyrics.ui.mediator.IMediator;
import com.findlyrics.ui.model.tablemodel.ITableModelPagination;
import com.findlyrics.ui.model.tablemodel.impl.OutputTableModel;
import com.findlyrics.ui.model.tablemodel.impl.PartialTableModel;


/**
 * Created by Padonag on 19.08.2014.
 */
public class UiModel {

    private OutputTableModel outputTableModel;
    private PartialTableModel partialTableModel;
    private IMediator mediator;

    public UiModel(IMediator mediator){
        this.mediator = mediator;
        mediator.registerModel(this);
    }

    public void createPartialTableModel(ILyricService service) throws DataConnectionException {
        partialTableModel = new PartialTableModel(service);
    }

    public OutputTableModel getOutputTableModel() {
        return outputTableModel;
    }

    public PartialTableModel getPartialTableModel() {
        return partialTableModel;
    }

    public ITableModelPagination getTableModel() {
        if (partialTableModel == null) {
            return outputTableModel;
        }
        return partialTableModel;
    }

    public void clearOutputTableModel() {
        outputTableModel = null;
    }

    public void clearPartialTableModel() {
        partialTableModel = null;
    }

    public IMediator getMediator() {
        return mediator;
    }
}
