package com.findlyrics.ui.view;

import com.findlyrics.ui.model.UiModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by Padonag on 19.08.2014.
 */
public class UiViewer extends JFrame {
    private ResourceBundle messages = ResourceBundle.getBundle("text", Locale.ENGLISH);

    private JTextField queryField = queryField = new JTextField(50);
    private JTable resultTable = new JTable();
    private JButton previousPage = new JButton(messages.getString("pagedown.button.name"));
    private JButton nextPage = new JButton(messages.getString("pageup.button.name"));
    private JButton searchButton = new JButton(messages.getString("search.button.name"));

    // test
    private UiModel model;

    public UiViewer(UiModel model) {
        this.model = model;
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        createForm(this);
        pack();
        this.setSize(640, 480);

    }

    private void createForm(final Container pane) {

        FlowLayout fl = new FlowLayout();
        pane.setLayout(fl);
        pane.add(queryField);
        pane.add(searchButton);
        pane.add(previousPage);
        pane.add(nextPage);
        pane.add(resultTable);


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