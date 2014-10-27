package com.findlyrics.ui.model.listmodel;

import com.findlyrics.ui.controller.ListController;
import com.findlyrics.ui.model.LyricItemDTO;
import com.findlyrics.ui.view.ShowLyricsFrame;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Created by Padonag on 19.10.2014.
 */
public class ListItem {
    private static final Logger log = Logger.getLogger(ListItem.class);
    private static final String EMPTY_STRING = "";
    private LyricItemDTO dto;
    private JButton button;
    private boolean isFromDB;

    public ListItem(final LyricItemDTO dto, boolean isFromDB) {
        this.dto = dto;
        this.isFromDB = isFromDB;
        this.button = new JButton();
        button.addActionListener(new LyricsListener());
    }

    public boolean isFromDB() {
        return isFromDB;
    }

    public JButton getButton() {
        return button;
    }

    public String getLyrics() {
        return dto.getLyrics();
    }

    @Override
    public String toString() {
        if (dto == null) {
            return EMPTY_STRING;
        }
        StringBuilder buttonName = new StringBuilder();
        buttonName.append(dto.getArtistName());
        buttonName.append(",     ");
        buttonName.append(dto.getSongName());
        return buttonName.toString();
    }

    public LyricItemDTO getDto() {
        return dto;
    }

    private class LyricsListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (isFromDB) {
                new ShowLyricsFrame(dto.getLyrics());
            } else {
                try {
                    String url = dto.getLyrics();
                    dto.setLyrics(getLyricsFromUrl(url));
                    new ShowLyricsFrame(dto.getLyrics());
                } catch (IOException e1) {
                    log.debug("Throwing exception ", e1);
                }
            }
        }

        private String getLyricsFromUrl(String url) throws IOException {
            Document document = Jsoup.connect(url).get();
            Element lyrics = document.select("pre").get(0);
            return lyrics.text();
        }

    }

}
