package com.findlyrics.ui.controller.listeners;

import com.findlyrics.ui.controller.ListController;
import com.findlyrics.ui.model.LyricItemDTO;
import com.findlyrics.ui.model.LyricsDTO;
import com.findlyrics.ui.model.listmodel.ListItem;
import org.apache.log4j.Logger;

import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by Padonag on 04.11.2014.
 */
public abstract class SearchButtonListener implements ActionListener, KeyListener{
    protected static final Logger log = Logger.getLogger(SubmitButtonListener.class);
    protected static final String EMPTY_STRING = "";
    protected ListController controller;
    protected ResourceBundle messages = ResourceBundle.getBundle("text", Locale.ENGLISH);

    protected SearchButtonListener(ListController controller) {
        this.controller = controller;
    }

    protected List<ListItem> convertToOutputList(LyricsDTO fullDto, boolean isDataFromDb) {
        List<ListItem> itemList = new LinkedList<ListItem>();
        for (LyricItemDTO dto : fullDto.getSearchResults()) {
            ListItem tempItem = new ListItem(dto, isDataFromDb);
            itemList.add(tempItem);
        }
        return itemList;
    }

    protected List<ListItem> checkItems(List<ListItem> dbItemList, List<ListItem> restItemList) {
        Iterator iterator = restItemList.iterator();
        while (iterator.hasNext()) {
            ListItem itemFromRest = (ListItem) iterator.next();
            for(ListItem itemFromDb : dbItemList){
                if(itemFromDb.getDto().getSongName().equals(itemFromRest.getDto().getSongName())){
                    iterator.remove();
                }
            }
        }

        return restItemList;
    }
}
