package com.findlyrics.ui.view.panels;

import com.findlyrics.ui.view.ListModelView;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by Padonag on 13.10.2014.
 */
public class QueryPanel extends JPanel {

    private static final String EMPTY_STRING = "";
    private ResourceBundle messages = ResourceBundle.getBundle("text", Locale.ENGLISH);

    private ListModelView view;
    private JButton searchButton;
    private JButton xButton;
    private JTextField queryField;

    public QueryPanel(ListModelView view) {
        this.view = view;
        setLayout(new FlowLayout());
        searchButton = new JButton(messages.getString("search.button.name"));
        xButton = new JButton(messages.getString("clear.text.button"));
        xButton.addActionListener(new XButtonListener());
        queryField = new JTextField(50);
        queryField.setText(EMPTY_STRING);
        add(queryField);
        add(xButton);
        add(searchButton);

    }

    public JTextField getQueryField() {
        return queryField;
    }

    public JButton getSearchButton() {
        return searchButton;
    }

    public JButton getxButton() {
        return xButton;
    }

    public void clearText() {
        queryField.setText(EMPTY_STRING);
    }

    public void setSearchButtonListener(ActionListener listener) {
        searchButton.addActionListener(listener);
    }

    private class XButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            clearText();
            view.getContentPanel().setVisible(false);
            view.getNextSearchPanel().setVisible(false);
        }
    }
}
