package main.java.com.findlyrics.ui.view;

import javax.swing.*;
import java.awt.*;

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

    private void closeFrame() {
        setVisible(false);
    }
}
