package com.findlyrics.ui.mediator;


import com.findlyrics.ui.controller.ListController;
import com.findlyrics.ui.mediator.buttons.impl.SearchButton;
import com.findlyrics.ui.mediator.buttons.impl.SearchMoreButton;
import com.findlyrics.ui.mediator.buttons.impl.SearchOnceMoreButton;
import com.findlyrics.ui.model.UiModel;
import com.findlyrics.ui.view.ListModelView;


/**
 * Created by Padonag on 03.10.2014.
 */
public interface IMediator {
    public void viewSearchMoreButton();

    public void viewSearchOnceMoreButton();

    public void viewSearchButton();

    public void registerSearchButton(SearchButton button);

    public void registerSearchMoreButton(SearchMoreButton button);

    public void registerSearchOnceMoreButton(SearchOnceMoreButton button);

    public void registerModel(UiModel model);

    public void registerView(ListModelView view);

    public void registerController(ListController controller);
}
