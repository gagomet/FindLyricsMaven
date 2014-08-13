package com.findlyrics.ui;

import com.findlyrics.ui.model.LyricItemDTO;
import com.findlyrics.ui.model.LyricsDTO;
import org.apache.log4j.Logger;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Padonag on 27.07.2014.
 */
public class OutputTableModel extends AbstractTableModel {

    public static final Logger log = Logger.getLogger(OutputTableModel.class);
    private final int VISIBLE_ON_PAGE = 20;
    private final String[] namesOfColumns = {"Artist", "Song name", "Lyrics"};
    private List<String[]> pageData;
    private int pageCount = 0;
    private int lastPageEntry;
    private int currentPage = 1;
    private int pageStartNumber = 0;
    private boolean isMiddlePage = true;
    private List<LyricItemDTO> results;

    public OutputTableModel(LyricsDTO dto) {
        this.results = dto.getSearchResults();

        if (results.size() <= VISIBLE_ON_PAGE) {
            this.pageCount = 1;
        } else {
            this.pageCount = results.size() / VISIBLE_ON_PAGE;
        }
        this.pageData = createPageData(pageStartNumber, VISIBLE_ON_PAGE);
        log.info("Creating Page Data in Constructor : " + pageData.toString());
        this.lastPageEntry = results.size() % VISIBLE_ON_PAGE;
        this.pageStartNumber += VISIBLE_ON_PAGE;
        System.out.println("number of pages = " + pageCount);
    }

    @Override
    public int getRowCount() {

        if (isMiddlePage) {
            return VISIBLE_ON_PAGE;
        }
        return lastPageEntry;
    }

    @Override
    public int getColumnCount() {
        return namesOfColumns.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return pageData.get(rowIndex)[columnIndex];
    }

    @Override
    public String getColumnName(int column) {
        return namesOfColumns[column];
    }

    public void nextPage() {
//        if (isMiddlePage) {
        if (currentPage < pageCount && (pageStartNumber + lastPageEntry) < results.size()) {
            pageData = createPageData(pageStartNumber, pageStartNumber + VISIBLE_ON_PAGE);
            log.info("Creating Page Data in nextPage : " + pageData.toString());
            pageStartNumber += VISIBLE_ON_PAGE;
            currentPage++;

        } else {
            isMiddlePage = !isMiddlePage;
            System.out.println(pageData.toString());
            createPageData(pageStartNumber, pageStartNumber + lastPageEntry);
            System.out.println(pageData.toString());

        }
//        }
        System.out.println("pageStartNumber= " + pageStartNumber + " current page = " + currentPage);
        System.out.println("songs = " + results.size());
    }

    public void previousPage() {
        if (currentPage != 0) {
            pageData = createPageData(pageStartNumber - VISIBLE_ON_PAGE, pageStartNumber);
            log.info("Creating Page Data in PreviousPage : " + pageData.toString());
            pageStartNumber -= VISIBLE_ON_PAGE;
            currentPage--;
            isMiddlePage = true;
        }
        System.out.println("pageStartNumber= " + pageStartNumber + " current page = " + currentPage);
    }

    public int getPageCount() {
        return pageCount;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    private ArrayList<String[]> createPageData(int begin, int end) {
        if (results.size() == 0) {
            return new ArrayList<String[]>();
        }
        if (results.size() < VISIBLE_ON_PAGE) {
            isMiddlePage = false;
            end = results.size();
        }

        ArrayList<String[]> pageData = new ArrayList<String[]>();
        for (int i = begin; i < end; ++i) {
            LyricItemDTO currentResult = results.get(i);
            String[] currentEntry = {currentResult.getArtistName(), currentResult.getSongName(), currentResult.getLyrics()};
            pageData.add(currentEntry);
        }
        return pageData;
    }


}
