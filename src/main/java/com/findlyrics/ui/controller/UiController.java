package com.findlyrics.ui.controller;

import com.findlyrics.http.service.HttpLyricsService;
import com.findlyrics.rest.model.service.RestLyricsService;
import com.findlyrics.service.ILyricService;
import com.findlyrics.service.impl.DBLyricsService;
import com.findlyrics.ui.model.UiModel;
import com.findlyrics.ui.view.ShowLyricsFrame;
import com.findlyrics.ui.view.UiViewer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by Padonag on 19.08.2014.
 */
public class UiController {

    private static final String EMPTY_STRING = "";

    private UiModel model;
    private UiViewer view;
    private ResourceBundle messages;


    public UiController(UiModel model, UiViewer view) {
        messages = ResourceBundle.getBundle("text", Locale.ENGLISH);
        this.model = model;
        this.view = view;
        view.addButtonsListener(new ButtonsListener());

    }

    private class ButtonsListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (EMPTY_STRING.equals(view.getQuery())) {
                view.showError(messages.getString("error.message"));
            } else {
                if (view.getSearchButton().getText().equals(messages.getString("search.button.name"))) {
                    ILyricService dbService = new DBLyricsService();
                    model.createTableModel(dbService, view.getQuery());
                    view.setSearchButton(new JButton(messages.getString("search.more.button.name")));
                } else if (view.getSearchButton().getText().equals(messages.getString("search.more.button.name"))) {
                    ILyricService dbService = new HttpLyricsService();
                    model.createTableModel(dbService, view.getQuery());
                    view.setSearchButton(new JButton(messages.getString("search.once.more.button.name")));
                } else if (view.getSearchButton().getText().equals(messages.getString("search.once.more.button.name"))) {
                    ILyricService dbService = new RestLyricsService();
                    model.createTableModel(dbService, view.getQuery());
                    view.setSearchButton(new JButton(messages.getString("search.once.more.button.name")));
                }
                if (model.getOutputTableModel().getPageCount() > 1) {
                    view.addPaginationButtons();
                    view.addPaginationListener(new PaginationListener());
                }
                if (model.getOutputTableModel().getPageCount() != 0) {
                    view.showTable();
                    view.addTableMouseAdapter(new TableMouseAdapter());
                }
            }
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

    private class TableMouseAdapter extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            super.mouseClicked(e);
            int row = view.getResultTable().rowAtPoint(e.getPoint());
            int column = view.getResultTable().columnAtPoint(e.getPoint());
            if (row >= 0 && column >= 2) {
                String text = (String) model.getOutputTableModel().getValueAt(view.getResultTable().getSelectedRow(), view.getResultTable().getSelectedColumn());
                new ShowLyricsFrame(text);
            }
        }
    }
}
