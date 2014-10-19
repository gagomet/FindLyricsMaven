package com.findlyrics.ui.controller;

import com.findlyrics.ui.model.UiModel;
import com.findlyrics.ui.view.ListModelView;
import com.findlyrics.ui.view.listeners.impl.SearchButtonListener;

/**
 * Created by Padonag on 15.10.2014.
 */
public class ListController {

    private UiModel model;
    private ListModelView view;
    private SearchButtonListener searchButtonListener = new SearchButtonListener(this);
    boolean isDbSearch = true;

    public ListController(UiModel model, ListModelView view) {
        this.model = model;
        this.view = view;
        view.getQueryPanel().setSearchButtonListener(searchButtonListener);
        view.getNextSearchPanel().setNextSearchButtonListener(searchButtonListener);
        view.getContentPanel().setListModel(model.getListModel());
    }

    public UiModel getModel() {
        return model;
    }

    public ListModelView getView() {
        return view;
    }

    public boolean isDbSearch() {
        return isDbSearch;
    }

    public void setDbSearch(boolean isDbSearch) {
        this.isDbSearch = isDbSearch;
    }

    //TODO write full controller class for list model (dynamical button next)
}
