package com.findlyrics.ui.model;

import org.apache.log4j.Logger;

import javax.swing.table.AbstractTableModel;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Padonag on 27.07.2014.
 */
public class OutputTableModel extends AbstractTableModel implements ITableModelPagination {

    public static final Logger log = Logger.getLogger(OutputTableModel.class);
    private static final int VISIBLE_ON_PAGE = 20;
    private static final String[] namesOfColumns = {"Artist", "Song name", "Lyrics"};

    private List<String[]> pageData;
    private int pageCount = 0;
    private int currentPage = 0;
    private List<LyricItemDTO> results;

    public OutputTableModel(LyricsDTO dto) {
        this.results = dto.getSearchResults();
        this.pageCount = (results.size() + VISIBLE_ON_PAGE - 1) / VISIBLE_ON_PAGE;
        this.pageData = createPageData();
    }

    @Override
    public int getRowCount() {
        return Math.min(VISIBLE_ON_PAGE, pageData.size());
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
        if (currentPage < (pageCount - 1)) {
            currentPage++;
            pageData = createPageData();
        }
    }

    public void previousPage() {
        if (currentPage > 0) {
            currentPage--;
            pageData = createPageData();
        }
    }

    @Override
    public void refreshData() {
        this.fireTableDataChanged();
    }

    public int getPageCount() {
        return pageCount;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    private List<String[]> createPageData() {
        List<String[]> pageData = new LinkedList<String[]>();

        int fromIndex = currentPage * VISIBLE_ON_PAGE;
        int toIndex = Math.min((currentPage + 1) * VISIBLE_ON_PAGE, results.size());

        List<LyricItemDTO> itemDTOs = results.subList(fromIndex, toIndex);
        for (LyricItemDTO dto : itemDTOs) {
            pageData.add(new String[]{dto.getArtistName(), dto.getSongName(), dto.getLyrics()});
        }
        return pageData;
    }
}
