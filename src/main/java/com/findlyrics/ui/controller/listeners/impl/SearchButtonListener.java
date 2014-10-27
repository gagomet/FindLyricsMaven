package com.findlyrics.ui.controller.listeners.impl;

import com.findlyrics.db.service.ILyricService;
import com.findlyrics.db.service.impl.DBLyricsService;
import com.findlyrics.exceptions.DataConnectionException;
import com.findlyrics.rest.service.ProxyRestLyricsService;
import com.findlyrics.ui.controller.ListController;
import com.findlyrics.ui.controller.listeners.IButtonListener;
import com.findlyrics.ui.model.LyricItemDTO;
import com.findlyrics.ui.model.LyricsDTO;
import com.findlyrics.ui.model.listmodel.ListItem;
import org.apache.log4j.Logger;

import java.awt.event.ActionEvent;
import java.util.Iterator;
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
                if (e.getSource() == controller.getView().getQueryPanel().getSearchButton()) {
                    searchInDb();
                } else searchInRest();
            } catch (DataConnectionException ex) {
                log.debug("Throwing exception ", ex);
            }
        }

    }

    private void searchInDb() throws DataConnectionException {
        controller.getModel().getListModel().clear();
        ILyricService dbService = DBLyricsService.factory.getInstance();
        LyricsDTO fullDto = dbService.hibernateGetFullDto(controller.getView().getQueryPanel().getQueryField().getText());
        controller.getModel().getListModel().setList(convertToOutputList(fullDto, true));
        controller.getView().getContentPanel().setVisible(true);
        controller.getView().getNextSearchPanel().setVisible(true);
        controller.getView().getContentPanel().setNumberOfFound();
    }

    private void searchInRest() throws DataConnectionException {
        ILyricService restService = ProxyRestLyricsService.factory.getInstance();
        LyricsDTO restDto = restService.getFullDto(controller.getView().getQueryPanel().getQueryField().getText());
        List<ListItem> dataFromRest = checkItems(controller.getModel().getListModel().getList(), convertToOutputList(restDto, false));
        controller.getModel().getListModel().addAll(dataFromRest);
        controller.getView().getContentPanel().setNumberOfFound();
    }

    private List<ListItem> convertToOutputList(LyricsDTO fullDto, boolean isDataFromDb) {
        List<ListItem> itemList = new LinkedList<ListItem>();
        for (LyricItemDTO dto : fullDto.getSearchResults()) {
            ListItem tempItem = new ListItem(dto, isDataFromDb);
            itemList.add(tempItem);
        }
        return itemList;
    }

    private List<ListItem> checkItems(List<ListItem> dbItemList, List<ListItem> restItemList) {
        for (ListItem itemFromDb : dbItemList) {
            for(ListItem itemFromRest : restItemList){
                if(itemFromDb.getDto().getSongName().equals(itemFromRest.getDto().getSongName()));
            }
        }
        return restItemList;
    }

}
