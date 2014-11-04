package com.findlyrics.ui.controller.listeners;

import com.findlyrics.db.service.ILyricService;
import com.findlyrics.db.service.impl.DBLyricsService;
import com.findlyrics.exceptions.DataConnectionException;
import com.findlyrics.ui.controller.ListController;
import com.findlyrics.ui.model.LyricsDTO;

import java.awt.AWTEvent;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

/**
 * Created by Padonag on 04.11.2014.
 */
public class SubmitButtonListener extends SearchButtonListener {

    public SubmitButtonListener(ListController controller) {
        super(controller);
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

        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            getAction(e);
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    private void getAction(AWTEvent e) {
        if (EMPTY_STRING.equals(super.controller.getView().getQueryPanel().getQueryField().getText())) {
            controller.getView().showError(messages.getString("error.message"));
        } else {
            try {
                searchInDb();
            } catch (DataConnectionException ex) {
                log.debug("Throwing exception ", ex);
            }
        }
    }

    private void searchInDb() throws DataConnectionException {
        super.controller.getModel().getListModel().clear();
        ILyricService dbService = DBLyricsService.factory.getInstance();
        LyricsDTO fullDto = dbService.hibernateGetFullDto(super.controller.getView().getQueryPanel().getQueryField().getText());
        super.controller.getModel().getListModel().setList(super.convertToOutputList(fullDto, true));
        super.controller.getView().getContentPanel().setVisible(true);
        super.controller.getView().getNextSearchPanel().setVisible(true);
        super.controller.getView().getContentPanel().setNumberOfFound();
    }

}
