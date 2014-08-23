package com.findlyrics.ui.view;

import com.findlyrics.ui.model.UiModel;
import com.findlyrics.util.PropertiesManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by Padonag on 19.08.2014.
 */
public class UiViewer extends JFrame {
    private static final int FRAME_WIDTH = 800;
    private static final int FRAME_HEIGHT = 600;
    private static final String BACKGROUND_IMAGE = "/resources/bground.jpg";

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
    private JPanel bgPanel;

    private UiModel model;

    public UiViewer(UiModel model) {

        bgPanel = new PaneWithBackground();
        this.setContentPane(bgPanel);
        this.model = model;
        this.queryField = new JTextField(50);
        this.searchButton = new JButton(messages.getString("search.button.name"));
        this.clearTextButton = new JButton(messages.getString("clear.text.button"));
        this.resultTable = new JTable();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        createForm();
        pack();
        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        this.setResizable(false);

    }

    private void createForm(){
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        queryPanel = new JPanel();
        paginationPanel = new JPanel();
        resultTablePanel = new JPanel();
        queryPanel.add(queryField);
        queryPanel.add(clearTextButton);
        queryPanel.add(searchButton);
        this.add(queryPanel, this);
        this.add(paginationPanel, this);
        this.add(resultTablePanel, this);


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

    public void addTextClearButtonListener(ActionListener listener){clearTextButton.addActionListener(listener);}

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

    public void setTextInQueryField(String text){
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
        resultTablePanel.add(resultTable);
        validate();
        resultTablePanel.repaint();
    }

    private class PaneWithBackground extends JPanel{
        //TODO not working. Need to fix

        Image bgImage;
        public PaneWithBackground() {
            MediaTracker mt = new MediaTracker(this);
            bgImage = Toolkit.getDefaultToolkit().getImage(BACKGROUND_IMAGE);
            mt.addImage(bgImage, 0);
            try {
                mt.waitForAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(bgImage, 1, 1, null);

        }
    }

}