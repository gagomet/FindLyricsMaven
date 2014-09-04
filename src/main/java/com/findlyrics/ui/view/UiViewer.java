package com.findlyrics.ui.view;

import com.findlyrics.ui.model.UiModel;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by Padonag on 19.08.2014.
 */
public class UiViewer extends JFrame {
    private static final int FRAME_WIDTH = 800;
    private static final int FRAME_HEIGHT = 600;

    private ResourceBundle messages = ResourceBundle.getBundle("text", Locale.ENGLISH);

    private JTextField queryField;
    private JTable resultTable;
    private JButton previousPage;
    private JButton nextPage;
    private JButton searchButton;
    private JButton clearTextButton;
    private JPanel queryPanel;
    private JPanel paginationPanel;
    private JPanel resultTablePanel;
    private UiModel model;

    public UiViewer(UiModel model) {

        this.model = model;
        this.queryField = new JTextField(50);
        this.searchButton = new JButton(messages.getString("search.button.name"));
        this.clearTextButton = new JButton(messages.getString("clear.text.button"));
        this.resultTable = new JTable();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        createForm();
        pack();
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setResizable(false);
    }

    private void createForm() {

        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        queryPanel = new JPanel();
        paginationPanel = new JPanel();
        resultTablePanel = new JPanel();
        queryPanel.add(queryField);
        queryPanel.add(clearTextButton);
        queryPanel.add(searchButton);
        add(queryPanel, this);
        add(paginationPanel, this);
        add(resultTablePanel, this);
    }

    public void addPaginationButtons() {
        previousPage = new JButton(messages.getString("pagedown.button.name"));
        nextPage = new JButton(messages.getString("pageup.button.name"));
        paginationPanel.add(previousPage);
        paginationPanel.add(nextPage);
        repaint();
    }

    public void addSearchButtonsListener(ActionListener listener) {
        searchButton.addActionListener(listener);
    }

    public void addTextClearButtonListener(ActionListener listener) {
        clearTextButton.addActionListener(listener);
    }

    public void addTextFieldListener(ActionListener listener) {queryField.addActionListener(listener);}

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

    public void setTextInQueryField(String text) {
        queryField.setText(text);
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

    public void setSearchButton(String buttonName) {
        searchButton.setVisible(false);
        remove(searchButton);
        searchButton = new JButton(buttonName);
        queryPanel.add(searchButton);
        queryPanel.repaint();
        this.repaint();
    }

    public void showError(String errorMessage) {
        JOptionPane.showMessageDialog(null, errorMessage);
    }

    public void showTable() {
        if (this.resultTable != null) {
            resultTablePanel.remove(resultTable);
        }

        resultTable = new JTable(model.getOutputTableModel());
        resultTable.getColumnModel().getColumn(0).setPreferredWidth(100);
        resultTable.getColumnModel().getColumn(1).setPreferredWidth(200);
        resultTable.getColumnModel().getColumn(2).setPreferredWidth(300);
        resultTable.setShowGrid(false);
        LayoutManager manager = new BorderLayout();
        resultTablePanel.setLayout(manager);
        resultTablePanel.add(resultTable, BorderLayout.NORTH);
        validate();
        resultTablePanel.repaint();
    }

    public JPanel getPaginationPanel() {
        return paginationPanel;
    }

    public JPanel getResultTablePanel() {
        return resultTablePanel;
    }
}