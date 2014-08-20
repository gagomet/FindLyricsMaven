
package com.findlyrics.ui.controller;

import com.findlyrics.http.service.HttpLyricsService;
import com.findlyrics.rest.model.service.RestLyricsService;
import com.findlyrics.service.ILyricService;
import com.findlyrics.service.impl.DBLyricsService;
import com.findlyrics.ui.ShowLyricsFrame;
import com.findlyrics.ui.model.UiModel;
import com.findlyrics.ui.view.UiView;

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
    private UiModel model;
    private UiView view;
    private ResourceBundle messages;


    public UiController(UiModel model, UiView view) {
        messages = ResourceBundle.getBundle("text", Locale.ENGLISH);
        this.model = model;
        this.view = view;
        view.addButtonsListener(new ButtonsListener());
        view.addPaginationListener(new PaginationListener());
        view.addTableMouseAdapter(new TableMouseAdapter());
    }

    private class ButtonsListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (view.getQuery().equals("")) {
                view.showError(messages.getString("error.message"));
            } else {
                if (view.getSearchButton().getText() == messages.getString("search.button.name")) {
                    ILyricService dbService = new DBLyricsService();
                    model.createTableModel(dbService, view.getQuery());
                    view.setSearchButton(new JButton(messages.getString("search.more.button.name")));
                } else if (view.getSearchButton().getText() == messages.getString("search.more.button.name")) {
                    ILyricService dbService = new HttpLyricsService();
                    model.createTableModel(dbService, view.getQuery());
                    view.setSearchButton(new JButton(messages.getString("search.once.more.button.name")));
                } else if (view.getSearchButton().getText() == messages.getString("search.once.more.button.name")) {
                    ILyricService dbService = new RestLyricsService();
                    model.createTableModel(dbService, view.getQuery());
                    view.setSearchButton(new JButton(messages.getString("search.once.more.button.name")));
                }
                if (model.getOutputTableModel().getPageCount() != 0) {
                    view.showTable();
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
