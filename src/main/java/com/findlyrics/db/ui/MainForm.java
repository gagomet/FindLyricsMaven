package com.findlyrics.db.ui;

import com.findlyrics.db.ConnectionManager;
import com.findlyrics.db.PropertiesManager;
import com.findlyrics.db.dao.implementations.ArtistDAO;
import com.findlyrics.db.dao.implementations.SongDAO;
import com.findlyrics.db.service.implementetions.LyricsService;
import com.findlyrics.db.ui.model.LyricsDTO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
    private ConnectionManager connectionManager;


    public MainForm(String properties) {

        this.connectionManager = new ConnectionManager(new PropertiesManager(properties));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        this.setSize(640, 480);
        createForm(this);
        pack();
        setVisible(true);
    }

    private void createForm(Container pane) {
        FlowLayout fl = new FlowLayout();
        pane.setLayout(fl);
        queryField = new JTextField(50);
        pane.add(queryField);
        searchButton = new JButton("Search!");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SongDAO songDAO = new SongDAO(connectionManager);
                ArtistDAO artistDAO = new ArtistDAO(connectionManager);
                LyricsDTO lyricsDTO = new LyricsDTO(new LyricsService(artistDAO, songDAO).getArtist(queryField.getText()));
                OutputTableModel model = new OutputTableModel(lyricsDTO);
                setTableModel(model);
                showTable();
                queryField.setText("");
                refreshForm();
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
            this.add(resultTable);

            if (tableModel.getPageCount() > 1) {
                previousPage = new JButton("Prev.Page");
                previousPage.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println("Button Previous Pressed!");
                        tableModel.previousPage();
                        tableModel.fireTableDataChanged();
                    }
                });

                this.add(previousPage);

                nextPage = new JButton("Next Page");
                nextPage.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println("Button Next Pressed!");
                        tableModel.nextPage();
                        tableModel.fireTableDataChanged();

                    }
                });
                this.add(nextPage);

            }

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
}
