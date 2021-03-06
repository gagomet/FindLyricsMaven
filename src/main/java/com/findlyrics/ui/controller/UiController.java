package com.findlyrics.ui.controller;

import com.findlyrics.db.service.ILyricService;
import com.findlyrics.db.service.IServiceFactory;
import com.findlyrics.db.service.impl.DBLyricsService;
import com.findlyrics.exceptions.DataConnectionException;
import com.findlyrics.http.service.HttpLyricsService;
import com.findlyrics.rest.service.RestLyricsService;
import com.findlyrics.ui.mediator.IMediator;
import com.findlyrics.ui.model.LyricItemDTO;
import com.findlyrics.ui.model.UiModel;
import com.findlyrics.ui.model.tablemodel.impl.OutputTableModel;
import com.findlyrics.ui.view.ShowLyricsFrame;
import com.findlyrics.ui.view.UiViewer;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import javax.swing.JTable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
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
    private IMediator mediator;

    public UiController(UiModel model, UiViewer view) {
        this.mediator = model.getMediator();
        messages = ResourceBundle.getBundle("text", Locale.ENGLISH);
        this.model = model;
        this.view = view;
        mediator.registerController(this);
        view.addTextFieldListener(new SearchButtonListener());
        view.addSearchButtonsListener(new SearchButtonListener());
        view.addTextClearButtonListener(new ClearTextListener());
    }

    public void constructOutput(IServiceFactory factory, MouseAdapter adapter) {
            createServiceAndModel(factory);
            addPagination();
            addTable(adapter);
    }

    public TableMouseAdapter getTableMouseAdapter() {
        return new TableMouseAdapter();
    }

    public TableMouseAdapterViewOnly getTableMouseAdapterViewOnly() {
        return new TableMouseAdapterViewOnly();
    }

    public TableWithUrlMouseAdapter getTableWithUrlMouseAdapter() {
        return new TableWithUrlMouseAdapter();
    }

    private void addPagination() {
        view.getPaginationPanel().setVisible(true);
        if (model.getTableModel().getPageCount() > 1) {
            if (view.getPreviousPage() == null && view.getNextPage() == null) {
                view.addPaginationButtons();
            }
            view.addPaginationListener(new PaginationListener());
        }
    }

    private void addTable(MouseAdapter adapter) {
        view.getResultTablePanel().setVisible(true);
        if (model.getTableModel().getPageCount() != 0) {
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
                constructOutput(DBLyricsService.factory, getTableMouseAdapterViewOnly());
                mediator.viewSearchMoreButton();
                view.addSearchMoreButtonsListener(new SearchMoreButtonListener());
                view.addTextFieldListener(new SearchOnceMoreButtonListener());
            }
        }
    }

    private class SearchMoreButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            constructOutput(HttpLyricsService.factory, getTableMouseAdapter());
            mediator.viewSearchOnceMoreButton();
            view.addSearchOnceMoreButtonsListener(new SearchOnceMoreButtonListener());
            view.addTextFieldListener(new SearchOnceMoreButtonListener());
        }
    }

    private class SearchOnceMoreButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            constructOutput(RestLyricsService.factory, getTableWithUrlMouseAdapter());
        }
    }

    private class ClearTextListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            view.setTextInQueryField(EMPTY_STRING);
            view.getPaginationPanel().setVisible(false);
            view.getResultTablePanel().setVisible(false);
            mediator.viewSearchButton();
            view.addSearchButtonsListener(new SearchButtonListener());
            model.clearOutputTableModel();
            model.clearPartialTableModel();
        }
    }

    private class PaginationListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == view.getPreviousPage()) {
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
                String text = (String) model.getPartialTableModel().getValueAt(view.getResultTable().getSelectedRow(), view.getResultTable().getSelectedColumn());
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
                    DBLyricsService service = (DBLyricsService) DBLyricsService.factory.getInstance();
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
                    //TODO can't use factory here
                    DBLyricsService service = (DBLyricsService) DBLyricsService.factory.getInstance();
                    LyricItemDTO itemDTO = getDtoToAdd(view.getResultTable(), model.getOutputTableModel());
                    itemDTO.setLyrics(getLyricsFromUrl(htmlPath));
                    service.addSongToDB(itemDTO);
                } catch (IOException e1) {
                    log.debug("Throwing exception", e1);
                } catch (DataConnectionException e1) {
                    log.debug("Throwing exception", e1);
                    view.showError(e1.getMessage());
                }
            }
        }
    }

    private String getLyricsFromUrl(String url) throws IOException {
        Document document = Jsoup.connect(url).get();
        Element lyrics = document.select("pre").get(0);
        return lyrics.text();
    }

    private LyricItemDTO getDtoToAdd(JTable resultTable, OutputTableModel tableModel) {
        int rowNumber = resultTable.getSelectedRow();
        String artistName = (String) tableModel.getValueAt(rowNumber, 0);
        String songTitle = (String) tableModel.getValueAt(rowNumber, 1);
        String lyrics = (String) tableModel.getValueAt(rowNumber, 2);
        return new LyricItemDTO(artistName, songTitle, lyrics);
    }

    private void createServiceAndModel(IServiceFactory factory) {
        ILyricService service = factory.getInstance();
        try {
            service.setQuery(view.getQuery());
            model.createPartialTableModel(service);
        } catch (DataConnectionException e1) {
            log.debug("Throwing exception", e1);
            view.showError(e1.getMessage());
        }
    }
}
