package com.findlyrics.ui.mediator.buttons.impl;

import com.findlyrics.ui.mediator.IMediator;
import com.findlyrics.ui.mediator.buttons.IUiSearchButton;

import javax.swing.JButton;

/**
 * Created by Padonag on 03.10.2014.
 */
public class SearchOnceMoreButton extends JButton implements IUiSearchButton {

    private IMediator mediator;

    public SearchOnceMoreButton(IMediator mediator, String text) {
        super(text);
        this.mediator = mediator;
        mediator.registerSearchOnceMoreButton(this);
    }

    @Override
    public void click() {
        mediator.viewSearchButton();
    }
}
