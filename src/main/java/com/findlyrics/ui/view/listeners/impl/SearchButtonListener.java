package com.findlyrics.ui.view.listeners.impl;

import com.findlyrics.db.service.ILyricService;
import com.findlyrics.db.service.impl.DBLyricsService;
import com.findlyrics.exceptions.DataConnectionException;
import com.findlyrics.rest.service.RestLyricsService;
import com.findlyrics.ui.controller.ListController;
import com.findlyrics.ui.model.LyricItemDTO;
import com.findlyrics.ui.model.LyricsDTO;
import com.findlyrics.ui.model.listmodel.ListItem;
import com.findlyrics.ui.view.listeners.IButtonListener;
import org.apache.log4j.Logger;

import java.awt.event.ActionEvent;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by Padonag on 13.10.2014.
 */
public class SearchButtonListener implements IButtonListener {

    private static final Logger log = Logger.getLogger(SearchButtonListener.class);
    private static final String EMPTY_STRING = "";
    private ListController controller;
    private ResourceBundle messages = ResourceBundle.getBundle("text", Locale.ENGLISH);

    public SearchButtonListener(ListController controller) {
        this.controller = controller;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (EMPTY_STRING.equals(controller.getView().getQueryPanel().getQueryField().getText())) {
            controller.getView().showError(messages.getString("error.message"));
        } else {
            try {
                if (controller.isDbSearch()) {
                    searchInDb();
                } else searchInRest();
            } catch (DataConnectionException ex) {
                log.debug("Throwing exception ", ex);
            }
        }

    }

    private void searchInDb() throws DataConnectionException {
        ILyricService dbService = DBLyricsService.factory.getInstance();
        LyricsDTO fullDto = dbService.getFullDto(controller.getView().getQueryPanel().getQueryField().getText());
        controller.getModel().getListModel().setList(convertToOutputList(fullDto, true));
        controller.getView().getContentPanel().setVisible(true);
        controller.getView().getNextSearchPanel().setVisible(true);
        controller.setDbSearch(false);
    }

    private void searchInRest() throws DataConnectionException {
        ILyricService restService = RestLyricsService.factory.getInstance();
        LyricsDTO fullDto = restService.getFullDto(controller.getView().getQueryPanel().getQueryField().getText());
        controller.getModel().getListModel().addAll(convertToOutputList(fullDto, false));
        controller.setDbSearch(true);
    }

    private List<ListItem> convertToOutputList(LyricsDTO fullDto, boolean isDataFromDb) {
        List<ListItem> itemList = new LinkedList<ListItem>();
        for (LyricItemDTO dto : fullDto.getSearchResults()) {
            ListItem tempItem = new ListItem(dto, isDataFromDb);
            itemList.add(tempItem);
        }
        return itemList;
    }


}
