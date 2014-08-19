package com.findlyrics.ui;

import com.findlyrics.http.service.HttpLyricsService;
import com.findlyrics.rest.model.service.RestLyricsService;
import com.findlyrics.service.ILyricService;
import com.findlyrics.service.impl.DBLyricsService;
import com.findlyrics.ui.model.LyricsDTO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by Padonag on 27.07.2014.
 */
public class MainForm extends JFrame {

    private OutputTableModel tableModel;
    private JTextField queryField;
    private JTable resultTable;
    private JButton previousPage;
    private JButton nextPage;
    private JButton searchMoreButton;
    private ResourceBundle messages;


    public MainForm() {
        messages = ResourceBundle.getBundle("text", Locale.ENGLISH);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        createForm(this);
        pack();
        this.setSize(640, 480);
        setVisible(true);

    }

    private void createForm(final Container pane) {

        FlowLayout fl = new FlowLayout();
        pane.setLayout(fl);
        queryField = new JTextField(50);
        pane.add(queryField);
        JButton searchButton = new JButton(messages.getString("search.button.name"));
        ILyricService dbService = new DBLyricsService();
        searchButton.addActionListener(listener(dbService));
        pane.add(searchButton);

    }


    public void setTableModel(OutputTableModel tableModel) {
        this.tableModel = tableModel;
    }

    private void showTable() {
        if (this.resultTable != null) {
            remove(resultTable);
        }
        if (tableModel != null) {

            resultTable = new JTable(tableModel);
            resultTable.getColumnModel().getColumn(0).setPreferredWidth(100);
            resultTable.getColumnModel().getColumn(1).setPreferredWidth(200);
            resultTable.getColumnModel().getColumn(2).setPreferredWidth(300);
            resultTable.setShowGrid(false);
            addButtons();
            this.add(resultTable);
            validate();

            resultTable.addMouseListener(adapter);

        }


    }

    private void refreshForm() {
        this.repaint();
    }

    MouseAdapter adapter = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            super.mouseClicked(e);
            int row = resultTable.rowAtPoint(e.getPoint());
            int column = resultTable.columnAtPoint(e.getPoint());
            if (row >= 0 && column >= 2) {
                String text = (String) tableModel.getValueAt(resultTable.getSelectedRow(), resultTable.getSelectedColumn());
                new ShowLyricsFrame(text);
            }
        }
    };

    private void addButtons() {
        if (tableModel.getPageCount() == 1) {
//            addRestButton();
            addSearchMoreButton();
        }
        if (previousPage == null && nextPage == null && tableModel.getPageCount() > 1) {
            previousPage = new JButton(messages.getString("pagedown.button.name"));
            previousPage.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Button Previous Pressed!");
                    tableModel.previousPage();
                    tableModel.fireTableDataChanged();
                }
            });

            this.add(previousPage);

            nextPage = new JButton(messages.getString("pageup.button.name"));
            nextPage.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Button Next Pressed!");
                    tableModel.nextPage();
                    tableModel.fireTableDataChanged();
//                    addRestButton();
                    addSearchMoreButton();

                }
            });
            this.add(nextPage);

        }
    }

//    private void addRestButton() {
//        if (searchMoreButton == null && tableModel.getCurrentPage() == tableModel.getPageCount() - 1) {
//            searchMoreButton = new JButton(messages.getString("rest.button.name"));
//            ILyricService restService = new RestLyricsService();
//            searchMoreButton.addActionListener(listener(restService));
//            this.add(searchMoreButton);
//            refreshForm();
//        }
//    }


    private ActionListener listener(final ILyricService service) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (queryField.getText().equals("")) {
                    ErrorSplashForm noQuery = new ErrorSplashForm(messages.getString("error.message"));
                }
                LyricsDTO lyricsDTO = service.getDTO(queryField.getText());
                OutputTableModel model = new OutputTableModel(lyricsDTO);
                if (model.getPageCount() != 0) {
                    setTableModel(model);
                    showTable();
                }
//                queryField.setText("");
                refreshForm();
            }
        };
    }

    private void addSearchMoreButton() {
        if (searchMoreButton == null && tableModel.getCurrentPage() == tableModel.getPageCount() - 1) {
            searchMoreButton = new JButton(messages.getString("search.more.button.name"));
            ILyricService service = new HttpLyricsService();
            searchMoreButton.addActionListener(listener(service));
            this.add(searchMoreButton);
            refreshForm();
        } else if (searchMoreButton != null) {
           this.remove(searchMoreButton);
            searchMoreButton = new JButton(messages.getString("search.more.button.name"));
            ILyricService service = new RestLyricsService();
            searchMoreButton.addActionListener(listener(service));
            this.add(searchMoreButton);
            refreshForm();
        }

    }

}