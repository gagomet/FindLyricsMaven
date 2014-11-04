package com.findlyrics.ui.controller.listeners;

import com.findlyrics.db.service.ILyricService;
import com.findlyrics.exceptions.DataConnectionException;
import com.findlyrics.rest.service.ProxyRestLyricsService;
import com.findlyrics.ui.controller.ListController;
import com.findlyrics.ui.model.LyricsDTO;
import com.findlyrics.ui.model.listmodel.ListItem;

import java.awt.AWTEvent;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.List;


/**
 * Created by Padonag on 04.11.2014.
 */
public class SearchMoreButtonListener extends SearchButtonListener {



    public SearchMoreButtonListener(ListController controller) {
        super(controller);
    }

    private void searchInRest() throws DataConnectionException {
        ILyricService restService = ProxyRestLyricsService.factory.getInstance();
        LyricsDTO restDto = restService.getFullDto(super.controller.getView().getQueryPanel().getQueryField().getText());
        List<ListItem> dataFromRest = super.checkItems(super.controller.getModel().getListModel().getList(), super.convertToOutputList(restDto, false));
        super.controller.getModel().getListModel().addAll(dataFromRest);
        super.controller.getView().getContentPanel().setNumberOfFound();
    }

    private void getAction(AWTEvent e){
        if (EMPTY_STRING.equals(controller.getView().getQueryPanel().getQueryField().getText())) {
            super.controller.getView().showError(messages.getString("error.message"));
        } else {
            try {
                searchInRest();
            } catch (DataConnectionException ex) {
                log.debug("Throwing exception ", ex);
            }
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        getAction(e);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
