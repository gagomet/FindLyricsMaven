package com.findlyrics.db.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Padonag on 02.08.2014.
 */
public class ShowLyricsFrame extends JFrame {
    private String lyrics;
    private JTextArea textArea;
    private JScrollPane scrollPane;

    public ShowLyricsFrame(String lyrics) {
        this.lyrics = lyrics;
        createFrame();
        pack();
        setVisible(true);
    }

    private void createFrame() {
        this.setDefaultCloseOperation(HIDE_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        getContentPane().setSize(screenSize.width/2, screenSize.height/2);
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textArea = new JTextArea(lyrics);
        textArea.setEditable(false);
        textArea.setCaretPosition(0);
        scrollPane = new JScrollPane(textArea);
        textPanel.add(scrollPane);
        this.add(textPanel);
        this.add(scrollPane);




    }

    private void closeFrame(){
        this.setVisible(false);

    }
}
