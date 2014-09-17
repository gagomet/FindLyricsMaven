package com.findlyrics.ui.controller;

import com.findlyrics.db.service.ILyricService;
import com.findlyrics.db.service.impl.DBLyricsService;
import com.findlyrics.db.service.impl.LyricServiceFactory;
import com.findlyrics.exceptions.DataConnectionException;
import com.findlyrics.type.ServiceType;
import com.findlyrics.ui.model.LyricItemDTO;
import com.findlyrics.ui.model.OutputTableModel;
import com.findlyrics.ui.model.UiModel;
import com.findlyrics.ui.view.ShowLyricsFrame;
import com.findlyrics.ui.view.UiViewer;
import org.apache.log4j.Logger;

import javax.swing.JTable;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.ConnectException;
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
        view.addTextFieldListener(new SearchButtonListener());
        view.addSearchButtonsListener(new SearchButtonListener());
        view.addTextClearButtonListener(new ClearTextListener());

    }

    private void addPagination() {
        view.getPaginationPanel().setVisible(true);
        if (/*model.getOutputTableModel().getPageCount()*/ model.getTableModel().getPageCount() > 1) {
            if (view.getPreviousPage() == null && view.getNextPage() == null) {
                view.addPaginationButtons();

            }
            view.addPaginationListener(new PaginationListener());
        }
    }

    private void addTable(MouseAdapter adapter) {
        view.getResultTablePanel().setVisible(true);
        if (/*model.getOutputTableModel().getPageCount()*/ model.getTableModel().getPageCount() != 0) {
            view.showTable();
            view.addTableMouseAdapter(adapter);
        }
    }

    /*private class SearchButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (EMPTY_STRING.equals(view.getQuery())) {
                view.showError(messages.getString("error.message"));
            } else {
                ILyricService dbService = LyricServiceFactory.getService(ServiceType.DB);
                try {
                    model.createTableModel(dbService, view.getQuery());
                } catch (DataConnectionException e1) {
                    log.debug("Throwing exception", e1);
                    view.showError(e1.getMessage());
                }
                addPagination();
                addTable(new TableMouseAdapterViewOnly());
                view.setSearchButton(messages.getString("search.more.button.name"));
                view.addSearchButtonsListener(new SearchMoreButtonListener());
                view.addTextFieldListener(new SearchOnceMoreButtonListener());
            }
        }
    }*/

    private class SearchButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (EMPTY_STRING.equals(view.getQuery())) {
                view.showError(messages.getString("error.message"));
            } else {
                DBLyricsService dbService = new DBLyricsService();
                try {
                    dbService.setQuery(view.getQuery());
                    model.createPartialTableModel(dbService);
                } catch (DataConnectionException e1) {
                    log.debug("Throwing exception", e1);
                    view.showError(e1.getMessage());
                }
                addPagination();
                addTable(new TableMouseAdapterViewOnly());
                view.setSearchButton(messages.getString("search.more.button.name"));
                view.addSearchButtonsListener(new SearchMoreButtonListener());
                view.addTextFieldListener(new SearchOnceMoreButtonListener());
            }
        }
    }

    private class SearchMoreButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (EMPTY_STRING.equals(view.getQuery())) {
                view.showError(messages.getString("error.message"));
            }
            ILyricService httpService = LyricServiceFactory.getService(ServiceType.HTTP);
            try {
                model.createTableModel(httpService, view.getQuery());
            } catch (DataConnectionException e1) {
                log.debug("Throwing exception", e1);
                view.showError(e1.getMessage());
            }
            addPagination();
            addTable(new TableMouseAdapter());
            view.setSearchButton(messages.getString("search.once.more.button.name"));
            view.addSearchButtonsListener(new SearchOnceMoreButtonListener());
            view.addTextFieldListener(new SearchOnceMoreButtonListener());
        }
    }

    private class SearchOnceMoreButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (EMPTY_STRING.equals(view.getQuery())) {
                view.showError(messages.getString("error.message"));
            }
            model.clearPartialTableModel();
            ILyricService restService = LyricServiceFactory.getService(ServiceType.REST);
            try {
                model.createTableModel(restService, view.getQuery());
            } catch (DataConnectionException e1) {
                log.debug("Throwing exception", e1);
                view.showError(e1.getMessage());
            }
            addPagination();
            addTable(new TableWithUrlMouseAdapter());
        }
    }

    private class ClearTextListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            view.setTextInQueryField(EMPTY_STRING);
            view.getPaginationPanel().setVisible(false);
            view.getResultTablePanel().setVisible(false);
            view.setSearchButton(messages.getString("search.button.name"));
            view.addSearchButtonsListener(new SearchButtonListener());
            model.clearOutputTableModel();
            model.clearPartialTableModel();
        }
    }


    private class PaginationListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            if (e.getSource() == view.getPreviousPage()) {

                /*System.out.println("Button Previous Pressed!");
                model.getOutputTableModel().previousPage();
                model.getOutputTableModel().fireTableDataChanged();*/
                System.out.println("Button Previous Pressed!");
                try {
                    model.getTableModel().previousPage();
                    model.getTableModel().refreshData();
                } catch (DataConnectionException e1) {
                    log.debug("Throwing exception", e1);
                    view.showError(e1.getMessage());
                }

            }

            if (e.getSource() == view.getNextPage()) {

                /*System.out.println("Button Next Pressed!");
                model.getOutputTableModel().nextPage();
                model.getOutputTableModel().fireTableDataChanged();*/
                System.out.println("Button Next Pressed!");
                try {
                    model.getTableModel().nextPage();
                    model.getTableModel().refreshData();
                } catch (DataConnectionException e1) {
                    log.debug("Throwing exception", e1);
                    view.showError(e1.getMessage());
                }
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
                    //TODO can't use factory here
                    DBLyricsService service = new DBLyricsService();
                    LyricItemDTO itemDTO = getDtoToAdd(view.getResultTable(), model.getOutputTableModel());
                    service.addSongToDB(itemDTO);
                } catch (DataConnectionException e1) {
                    log.debug("Throwing exception", e1);
                    view.showError(e1.getMessage());
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
                    //TODO can't use factory here
                    DBLyricsService service = new DBLyricsService();
                    LyricItemDTO itemDTO = getDtoToAdd(view.getResultTable(), model.getOutputTableModel());
                    itemDTO.setLyrics(service.getLyricsFromUrl(htmlPath));
                    System.out.println(itemDTO.getLyrics());
                    service.addSongToDB(itemDTO);

                } catch (IOException e1) {
                    log.debug("Throwing exception", e1);
                } catch (URISyntaxException e1) {
                    log.debug("Throwing exception", e1);
                } catch (DataConnectionException e1) {
                    log.debug("Throwing exception", e1);
                    view.showError(e1.getMessage());
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
