package com.findlyrics.ui;

import com.findlyrics.db.ConnectionManager;
import com.findlyrics.db.PropertiesManager;
import com.findlyrics.db.dao.implementations.ArtistDAO;
import com.findlyrics.db.dao.implementations.SongDAO;
import com.findlyrics.db.service.implementetions.LyricsService;
import com.findlyrics.rest.service.RestService;
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
    protected OutputTableModel tableModel;
    protected JTextField queryField;
    protected JButton searchButton;
    protected JTable resultTable;
    private JButton previousPage;
    private JButton nextPage;
    private JButton restButton;
    private ConnectionManager connectionManager;
    String currentQuery;
    Locale currentLocale = Locale.ENGLISH;
    ResourceBundle messages;


    public MainForm(String properties) {

        messages = ResourceBundle.getBundle("text", currentLocale);
        this.connectionManager = new ConnectionManager(new PropertiesManager(properties));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
        searchButton = new JButton(messages.getString("search.button.name"));
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (queryField.getText().equals("")) {
                    ErrorSplashForm noQuery = new ErrorSplashForm(messages.getString("error.message"));
                } else {
                    currentQuery = queryField.getText();
                    SongDAO songDAO = new SongDAO(connectionManager);
                    ArtistDAO artistDAO = new ArtistDAO(connectionManager);
                    LyricsService service = new LyricsService(artistDAO, songDAO);
                    LyricsDTO lyricsDTO = service.getDTOFromDB(service.getArtist(currentQuery));
                    OutputTableModel model = new OutputTableModel(lyricsDTO);
                    if (model.getPageCount() == 0) {

                    } else {
                        setTableModel(model);
                        showTable();
                    }
                    queryField.setText("");
                    refreshForm();

                }
            }
        });
        pane.add(searchButton);

    }


    public void setTableModel(OutputTableModel tableModel) {
        this.tableModel = tableModel;
    }

    public void showTable() {
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


            resultTable.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    int row = resultTable.rowAtPoint(e.getPoint());
                    int column = resultTable.columnAtPoint(e.getPoint());
                    if (row >= 0 && column >= 2) {
                        String text = (String) tableModel.getValueAt(resultTable.getSelectedRow(), resultTable.getSelectedColumn());
                        ShowLyricsFrame currentLyrics = new ShowLyricsFrame(text);
//                        JOptionPane.showMessageDialog(resultTable, text);
                    }
                }
            });
        }


    }

    public void refreshForm() {
        this.repaint();
    }

    private void addButtons() {
        if (previousPage == null && nextPage == null && tableModel.getPageCount() >= 1) {
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
                    addRestButton();

                }
            });
            this.add(nextPage);

        }
    }

    private void addRestButton() {
        if (restButton == null && tableModel.getCurrentPage() == tableModel.getPageCount()) {
            restButton = new JButton(messages.getString("rest.button.name"));
            restButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    RestService restService = new RestService();
                    LyricsDTO lyricsDTO = restService.getDTOFromRest(currentQuery);
                    OutputTableModel model = new OutputTableModel(lyricsDTO);
                    if (model.getPageCount() == 0) {

                    } else {
                        setTableModel(model);
                        showTable();
                    }
                    queryField.setText("");
                    refreshForm();

                }
            });
            this.add(restButton);
            refreshForm();
        }
    }

}
