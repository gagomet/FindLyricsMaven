package com.findlyrics.ui.controller;

import com.findlyrics.db.service.ILyricService;
import com.findlyrics.db.service.impl.DBLyricsService;
import com.findlyrics.exceptions.DbConnectionException;
import com.findlyrics.http.service.HttpLyricsService;
import com.findlyrics.rest.model.service.RestLyricsService;
import com.findlyrics.ui.model.LyricItemDTO;
import com.findlyrics.ui.model.OutputTableModel;
import com.findlyrics.ui.model.UiModel;
import com.findlyrics.ui.view.ShowLyricsFrame;
import com.findlyrics.ui.view.UiViewer;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by Padonag on 19.08.2014.
 */
public class UiController {

    private static final String EMPTY_STRING = "";
    private static final Logger log = Logger.getLogger(UiController.class);

    private UiModel model;
    private UiViewer view;
    private ResourceBundle messages;


    public UiController(UiModel model, UiViewer view) {
        messages = ResourceBundle.getBundle("text", Locale.ENGLISH);
        this.model = model;
        this.view = view;
        view.addSearchButtonsListener(new SearchButtonListener());
        view.addTextClearButtonListener(new ClearTextListener());

    }

    private void addPagination() {
        if (model.getOutputTableModel().getPageCount() > 1) {
            if (view.getPreviousPage() == null && view.getNextPage() == null) {
                view.addPaginationButtons();

            }
            view.addPaginationListener(new PaginationListener());
        }
    }

    private void addTable(MouseAdapter adapter) {
        if (model.getOutputTableModel().getPageCount() != 0) {
            view.showTable();
            view.addTableMouseAdapter(adapter);
        }
    }

    private class SearchButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (EMPTY_STRING.equals(view.getQuery())) {
                view.showError(messages.getString("error.message"));
            } else {
                ILyricService dbService = new DBLyricsService();
                try {
                    model.createTableModel(dbService, view.getQuery());
                } catch (DbConnectionException e1) {
                    log.debug("Throwing exception", e1);
                }
                addPagination();
                addTable(new TableMouseAdapterViewOnly());
                view.setSearchButton(messages.getString("search.more.button.name"));
                view.addSearchButtonsListener(new SearchMoreButtonListener());
            }
        }
    }

    private class SearchMoreButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (EMPTY_STRING.equals(view.getQuery())) {
                view.showError(messages.getString("error.message"));
            }
            ILyricService httpService = new HttpLyricsService();
            try {
                model.createTableModel(httpService, view.getQuery());
            } catch (DbConnectionException e1) {
                log.debug("Throwing exception", e1);
            }
            addPagination();
            addTable(new TableMouseAdapter());
            view.setSearchButton(messages.getString("search.once.more.button.name"));
            view.addSearchButtonsListener(new SearchOnceMoreButtonListener());
        }
    }

    private class SearchOnceMoreButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (EMPTY_STRING.equals(view.getQuery())) {
                view.showError(messages.getString("error.message"));
            }
            ILyricService restService = new RestLyricsService();
            try {
                model.createTableModel(restService, view.getQuery());
            } catch (DbConnectionException e1) {
                log.debug("Throwing exception", e1);
            }
            addPagination();
            addTable(new TableWithUrlMouseAdapter());
        }
    }

    private class ClearTextListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            view.setTextInQueryField(EMPTY_STRING);
        }
    }


    private class PaginationListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            if (e.getSource() == view.getPreviousPage()) {

                System.out.println("Button Previous Pressed!");
                model.getOutputTableModel().previousPage();
                model.getOutputTableModel().fireTableDataChanged();
            }

            if (e.getSource() == view.getNextPage()) {

                System.out.println("Button Next Pressed!");
                model.getOutputTableModel().nextPage();
                model.getOutputTableModel().fireTableDataChanged();
            }

        }
    }

    private class TableMouseAdapterViewOnly extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            super.mouseClicked(e);
            int row = view.getResultTable().rowAtPoint(e.getPoint());
            int column = view.getResultTable().columnAtPoint(e.getPoint());
            if (row >= 0 && column == 2) {
                String text = (String) model.getOutputTableModel().getValueAt(view.getResultTable().getSelectedRow(), view.getResultTable().getSelectedColumn());
                new ShowLyricsFrame(text);
            }
        }
    }

    private class TableMouseAdapter extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            super.mouseClicked(e);
            int row = view.getResultTable().rowAtPoint(e.getPoint());
            int column = view.getResultTable().columnAtPoint(e.getPoint());
            if (row >= 0 && column == 2) {
                try {
                    String text = (String) model.getOutputTableModel().getValueAt(view.getResultTable().getSelectedRow(), view.getResultTable().getSelectedColumn());
                    new ShowLyricsFrame(text);
                    DBLyricsService service = new DBLyricsService();
                    LyricItemDTO itemDTO = getDtoToAdd(view.getResultTable(), model.getOutputTableModel());
                    service.addSongToDB(itemDTO);
                } catch (DbConnectionException e1) {
                    log.debug("Throwing exception", e1);
                    //TODO handle exception and send it to UI
                }
            }
        }
    }

    private class TableWithUrlMouseAdapter extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            super.mouseClicked(e);
            int row = view.getResultTable().rowAtPoint(e.getPoint());
            int column = view.getResultTable().columnAtPoint(e.getPoint());
            if (row >= 0 && column == 2) {
                String htmlPath = (String) model.getOutputTableModel().getValueAt(view.getResultTable().getSelectedRow(), view.getResultTable().getSelectedColumn());
                try {
                    Desktop.getDesktop().browse(new URI(htmlPath));
                    DBLyricsService service = new DBLyricsService();
                    LyricItemDTO itemDTO = getDtoToAdd(view.getResultTable(), model.getOutputTableModel());
                    service.addSongToDB(itemDTO);

                } catch (IOException e1) {
                    log.debug("Throwing exception", e1);
                } catch (URISyntaxException e1) {
                    log.debug("Throwing exception", e1);
                } catch (DbConnectionException e1) {
                    log.debug("Throwing exception", e1);
                    //TODO handle exception and send it to UI
                }
            }
        }
    }

    private LyricItemDTO getDtoToAdd(JTable resultTable, OutputTableModel tableModel) {
        int rowNumber = resultTable.getSelectedRow();
        String artistName = (String) tableModel.getValueAt(rowNumber, 0);
        String songTitle = (String) tableModel.getValueAt(rowNumber, 1);
        String lyrics = (String) tableModel.getValueAt(rowNumber, 2);
        return new LyricItemDTO(artistName, songTitle, lyrics);
    }
}
