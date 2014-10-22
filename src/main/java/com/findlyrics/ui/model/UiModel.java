package com.findlyrics.ui.model;

import com.findlyrics.ui.mediator.IMediator;
import com.findlyrics.ui.model.listmodel.ArrayListModel;


/**
 * Created by Padonag on 19.08.2014.
 */
public class UiModel {

    private ArrayListModel<LyricItemDTO> listModel = new ArrayListModel<LyricItemDTO>();
    private IMediator mediator;

    public UiModel(IMediator mediator) {
        this.mediator = mediator;
        mediator.registerModel(this);
    }

    public ArrayListModel getListModel() {
        return listModel;
    }

    public void clearListModel() {
        listModel = null;
    }

    public IMediator getMediator() {
        return mediator;
    }
}
