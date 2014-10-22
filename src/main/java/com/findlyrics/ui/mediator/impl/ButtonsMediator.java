package com.findlyrics.ui.mediator.impl;

import com.findlyrics.ui.controller.ListController;
import com.findlyrics.ui.mediator.IMediator;
import com.findlyrics.ui.mediator.buttons.impl.SearchButton;
import com.findlyrics.ui.mediator.buttons.impl.SearchMoreButton;
import com.findlyrics.ui.mediator.buttons.impl.SearchOnceMoreButton;
import com.findlyrics.ui.model.UiModel;
import com.findlyrics.ui.view.ListModelView;


/**
 * Created by Padonag on 03.10.2014.
 */
public class ButtonsMediator implements IMediator {

    SearchButton searchButton;
    SearchMoreButton searchMoreButton;
    SearchOnceMoreButton searchOnceMoreButton;
    ListModelView view;
    ListController controller;
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
    public void registerView(ListModelView view) {
        this.view = view;
    }

    @Override
    public void registerController(ListController controller) {
        this.controller = controller;
    }


}
