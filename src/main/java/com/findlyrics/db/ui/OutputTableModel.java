package com.findlyrics.db.ui;

import com.findlyrics.db.model.SearchResult;
import com.findlyrics.db.ui.model.LyricsDTO;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Padonag on 27.07.2014.
 */
public class OutputTableModel extends AbstractTableModel {

    private final int VISIBLE_ON_PAGE = 20;
    private final int NUMBER_OF_COLUMNS = 3;
    private final String[] namesOfColumns = {"Artist", "Song name", "Lyrics"};
    private List<String[]> pageData;
    private int pageCount;
    private int lastPageEntry;
    private int currentPage = 1;
    private int pageStartNumber = 0;
    private boolean isMiddlePage = true;
    private List<SearchResult> results;

    public OutputTableModel(LyricsDTO dto){
        this.results = dto.getSearchResults();

        if (results.size() < VISIBLE_ON_PAGE) {
            this.pageCount = 1;
        }
        else{
            this.pageCount = results.size() / VISIBLE_ON_PAGE;
        }
        this.pageData = createPageData(0, VISIBLE_ON_PAGE);
        this.lastPageEntry = results.size() % VISIBLE_ON_PAGE;
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
        return NUMBER_OF_COLUMNS;
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
        if (isMiddlePage) {
            if (currentPage < pageCount) {
//                pageData = new ArrayList<String[]>();
                pageData = createPageData(pageStartNumber, pageStartNumber + VISIBLE_ON_PAGE);
                pageStartNumber += VISIBLE_ON_PAGE;
                currentPage++;
            } else {
                createPageData(pageStartNumber, pageStartNumber + lastPageEntry);
                isMiddlePage = !isMiddlePage;
            }
        }
        System.out.println("pageStartNumber= " + pageStartNumber + " current page = " + currentPage);
        System.out.println("songs = " + results.size());
    }

    public void previousPage() {
        if (currentPage != 1) {
//            pageData = new ArrayList<String[]>();
            pageData = createPageData(pageStartNumber - VISIBLE_ON_PAGE, pageStartNumber);
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
        if(results.size() == 0){
            return new ArrayList<String[]>();
        }
        if (results.size() < VISIBLE_ON_PAGE) {
            isMiddlePage = false;
            end = results.size();
        }
        ArrayList<String[]> pageData = new ArrayList<String[]>();
        for (int i = begin; i < end; i++) {
            SearchResult currentResult = results.get(i);
            String[] currentEntry = {currentResult.getArtistName(), currentResult.getSongName(), currentResult.getLyrics()};
            pageData.add(currentEntry);
        }
        return pageData;
    }


}
