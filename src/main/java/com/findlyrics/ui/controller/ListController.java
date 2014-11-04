package com.findlyrics.ui.controller;

import com.findlyrics.ui.controller.listeners.SearchButtonListener;
import com.findlyrics.ui.controller.listeners.ContentPaneMouseListener;
import com.findlyrics.ui.controller.listeners.SearchMoreButtonListener;
import com.findlyrics.ui.controller.listeners.SubmitButtonListener;
import com.findlyrics.ui.model.UiModel;
import com.findlyrics.ui.model.listmodel.ListItem;
import com.findlyrics.ui.view.ListModelView;

import javax.swing.JList;

/**
 * Created by Padonag on 15.10.2014.
 */
public class ListController {

    private UiModel model;
    private ListModelView view;
    private SearchButtonListener searchButtonListener = new SubmitButtonListener(this);
    private SearchButtonListener searchMoreButtonListener = new SearchMoreButtonListener(this);
    private ContentPaneMouseListener contentPaneMouseListener;

    public ListController(UiModel model, ListModelView view) {
        this.model = model;
        this.view = view;
        view.getQueryPanel().setSearchButtonListener(searchButtonListener);
        view.getNextSearchPanel().setNextSearchButtonListener(searchMoreButtonListener);
        view.getContentPanel().setListModel(model.getListModel());
        JList<ListItem> list = view.getContentPanel().getContentList();
        contentPaneMouseListener = new ContentPaneMouseListener(list);
        view.getContentPanel().setMouseListener(contentPaneMouseListener);
    }

    public UiModel getModel() {
        return model;
    }

    public ListModelView getView() {
        return view;
    }

    //TODO write full controller class for list model
}
