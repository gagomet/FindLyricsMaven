package com.findlyrics.ui.view.panels;

import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by Padonag on 13.10.2014.
 */
public class NextSearchPanel extends JPanel {
    private JButton nextSearchButton;
    private ResourceBundle messages = ResourceBundle.getBundle("text", Locale.ENGLISH);

    public NextSearchPanel() {
        setLayout(new FlowLayout());
        this.nextSearchButton = new JButton(messages.getString("search.more.button.name"));
        add(nextSearchButton);
    }

    public JButton getNextSearchButton() {
        return nextSearchButton;
    }

    public void setNextSearchButtonListener(ActionListener listener) {
        nextSearchButton.addActionListener(listener);
    }
}
