package com.findlyrics.ui.controller.listeners;

import com.findlyrics.db.service.ILyricService;
import com.findlyrics.db.service.impl.DBLyricsService;
import com.findlyrics.exceptions.DataConnectionException;
import com.findlyrics.ui.model.LyricItemDTO;
import com.findlyrics.ui.model.listmodel.ListItem;
import org.apache.log4j.Logger;

import javax.swing.JList;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by Padonag on 26.10.2014.
 */
public class ContentPaneMouseListener extends MouseAdapter {
    private static final Logger log = Logger.getLogger(ContentPaneMouseListener.class);
    private JList<ListItem> contentList;

    public ContentPaneMouseListener(JList<ListItem> contentList) {
        this.contentList = contentList;
    }

    public void mouseClicked(MouseEvent e){
        clickButtonAt(e.getPoint());
    }

    private void clickButtonAt(Point point) {
        int index = contentList.locationToIndex(point);
        ListItem item = contentList.getModel().getElementAt(index);
        if(!item.isFromDB()){
            try{
            addItemToDb(item);}
            catch (DataConnectionException e){
                log.debug("Throwing exception ", e);
            }
        }
        item.getButton().doClick();
    }

    private void addItemToDb(ListItem listItem) throws DataConnectionException{
        ILyricService dbService = DBLyricsService.factory.getInstance();
        LyricItemDTO lyricItemDTO = listItem.getDto();
        dbService.hibernateAddSongToDB(lyricItemDTO);
    }
}
