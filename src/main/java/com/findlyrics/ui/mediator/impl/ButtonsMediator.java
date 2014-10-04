package com.findlyrics.ui.mediator.impl;

import com.findlyrics.db.service.impl.DBLyricsService;
import com.findlyrics.ui.controller.UiController;
import com.findlyrics.ui.mediator.IMediator;
import com.findlyrics.ui.mediator.buttons.IUiSearchButton;
import com.findlyrics.ui.mediator.buttons.impl.SearchButton;
import com.findlyrics.ui.mediator.buttons.impl.SearchMoreButton;
import com.findlyrics.ui.mediator.buttons.impl.SearchOnceMoreButton;
import com.findlyrics.ui.model.UiModel;
import com.findlyrics.ui.view.UiViewer;

import java.awt.event.ActionListener;


/**
 * Created by Padonag on 03.10.2014.
 */
public class ButtonsMediator implements IMediator {

    SearchButton searchButton;
    SearchMoreButton searchMoreButton;
    SearchOnceMoreButton searchOnceMoreButton;
    UiViewer view;
    UiController controller;
    UiModel model;

    @Override
    public void viewSearchMoreButton() {
        searchButton.setEnabled(false);
        searchButton.setVisible(false);
        searchMoreButton.setVisible(true);
        searchMoreButton.setEnabled(true);
        searchOnceMoreButton.setVisible(false);
        searchOnceMoreButton.setEnabled(false);
    }

    @Override
    public void viewSearchOnceMoreButton() {
        searchButton.setEnabled(false);
        searchButton.setVisible(false);
        searchMoreButton.setEnabled(false);
        searchMoreButton.setVisible(false);
        searchOnceMoreButton.setVisible(true);
        searchOnceMoreButton.setEnabled(true);
    }

    @Override
    public void viewSearchButton() {
        searchButton.setEnabled(true);
        searchButton.setVisible(true);
        searchMoreButton.setEnabled(false);
        searchMoreButton.setVisible(false);
        searchOnceMoreButton.setVisible(false);
        searchOnceMoreButton.setEnabled(false);
    }

    @Override
    public void registerSearchButton(SearchButton button) {
        this.searchButton = button;
    }

    @Override
    public void registerSearchMoreButton(SearchMoreButton button) {
        this.searchMoreButton = button;
    }

    @Override
    public void registerSearchOnceMoreButton(SearchOnceMoreButton button) {
        this.searchOnceMoreButton = button;
    }

    @Override
    public void registerModel(UiModel model) {
        this.model = model;
    }

    @Override
    public void registerView(UiViewer view) {
        this.view = view;
    }

    @Override
    public void registerController(UiController controller) {
        this.controller = controller;
    }

    public void setSearchListener(){
        controller.constructOutput(DBLyricsService.factory, controller.getTableMouseAdapterViewOnly());

    }
}
