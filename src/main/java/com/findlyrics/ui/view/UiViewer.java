package com.findlyrics.ui.view;

import com.findlyrics.ui.model.UiModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by Padonag on 19.08.2014.
 */
public class UiViewer extends JFrame {
    private static final int FRAME_WIDTH = 640;
    private static final int FRAME_HEIGHT = 480;

    private ResourceBundle messages = ResourceBundle.getBundle("text", Locale.ENGLISH);

    private JTextField queryField;
    private JTable resultTable;
    private JButton previousPage;
    private JButton nextPage;
    private JButton searchButton;


    private UiModel model;

    public UiViewer(UiModel model) {
        this.model = model;
        this.queryField = new JTextField(50);
        this.searchButton = new JButton(messages.getString("search.button.name"));
        this.resultTable = new JTable();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        createForm();
        pack();
        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);

    }

    private void createForm() {

        FlowLayout fl = new FlowLayout();
        this.setLayout(fl);
        this.add(queryField);
        this.add(searchButton);
        this.add(resultTable);


    }

    public void addPaginationButtons(){
    previousPage = new JButton(messages.getString("pagedown.button.name"));
    nextPage = new JButton(messages.getString("pageup.button.name"));
        this.add(previousPage);
        this.add(nextPage);
        this.repaint();
    }


    public void addButtonsListener(ActionListener listener) {
        searchButton.addActionListener(listener);
    }

    public void addPaginationListener(ActionListener listener) {
        previousPage.addActionListener(listener);
        nextPage.addActionListener(listener);
    }

    public void addTableMouseAdapter(MouseAdapter listener) {
        resultTable.addMouseListener(listener);
    }

    public String getQuery() {
        return queryField.getText();
    }

    public JTable getResultTable() {
        return resultTable;
    }

    public JButton getPreviousPage() {
        return previousPage;
    }

    public JButton getNextPage() {
        return nextPage;
    }

    public JButton getSearchButton() {
        return searchButton;
    }

    public void setSearchButton(JButton searchButton) {
        this.searchButton = searchButton;
        validate();
        this.repaint();
    }

    public void showError(String errorMessage) {
        JOptionPane.showMessageDialog(null, errorMessage);
    }

    public void showTable() {
        if (this.resultTable != null) {
            remove(resultTable);
        }

        resultTable = new JTable(model.getOutputTableModel());
        resultTable.getColumnModel().getColumn(0).setPreferredWidth(100);
        resultTable.getColumnModel().getColumn(1).setPreferredWidth(200);
        resultTable.getColumnModel().getColumn(2).setPreferredWidth(300);
        resultTable.setShowGrid(false);
        this.add(resultTable);
        validate();
    }


}