package com.findlyrics.ui.view;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.Dimension;
import java.awt.Toolkit;

/**
 * Created by Padonag on 02.08.2014.
 */
public class ShowLyricsFrame extends JFrame {
    private String lyrics;

    public ShowLyricsFrame(String lyrics) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.lyrics = lyrics;
        createFrame();
        pack();
        setLocationRelativeTo(null);
        setSize(screenSize.width / 2, screenSize.height / 2);
        setVisible(true);
    }

    private void createFrame() {
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        JTextArea textArea = new JTextArea(lyrics);
        textArea.setEditable(false);
        textArea.setCaretPosition(0);
        JScrollPane scrollPane = new JScrollPane(textArea);
        textPanel.add(scrollPane);
        add(textPanel);
        add(scrollPane);
    }
}
