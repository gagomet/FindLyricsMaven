package com.findlyrics.ui.model.tablemodel.impl;

import com.findlyrics.db.service.ILyricService;
import com.findlyrics.exceptions.DataConnectionException;
import com.findlyrics.ui.model.LyricItemDTO;
import com.findlyrics.ui.model.LyricsDTO;
import com.findlyrics.ui.model.tablemodel.ITableModelPagination;

import javax.swing.table.AbstractTableModel;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Padonag on 14.09.2014.
 */
public class PartialTableModel extends AbstractTableModel implements ITableModelPagination {
    private static final String[] namesOfColumns = {"Artist", "Song name", "Lyrics"};
    private static final int RECORDS_PER_PAGE = 20;
    private int currentPage = 0;
    private int noOfPages = 0;
    private int noOfRecords;
    private List<String[]> pageData;
    private ILyricService service;

    public PartialTableModel(ILyricService service) throws DataConnectionException {
        this.service = service;
        this.pageData = createPageData();
        this.noOfRecords = service.getNumberOfRecords();
        this.noOfPages = (noOfRecords + RECORDS_PER_PAGE - 1) / RECORDS_PER_PAGE;

    }

    @Override
    public int getRowCount() {
        return pageData.size();
    }

    @Override
    public int getColumnCount() {
        return namesOfColumns.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return pageData.get(rowIndex)[columnIndex];
    }

    public void nextPage() throws DataConnectionException {
        if (currentPage < noOfPages - 1) {
            currentPage++;
            pageData = createPageData();
        }

    }

    public void previousPage() throws DataConnectionException {
        if (currentPage > 0) {
            currentPage--;
            pageData = createPageData();
        }

    }

    @Override
    public void refreshData() {
        this.fireTableDataChanged();
    }

    private List<String[]> createPageData() throws DataConnectionException {
        LyricsDTO results = service.getPartDTO(currentPage, RECORDS_PER_PAGE);
        List<String[]> pageData = new LinkedList<String[]>();
        List<LyricItemDTO> itemDTOs = results.getSearchResults();
        for (LyricItemDTO currentDTO : itemDTOs) {
            pageData.add(new String[]{currentDTO.getArtistName(), currentDTO.getSongName(), currentDTO.getLyrics()});
        }
        return pageData;
    }

    public int getPageCount() {
        return noOfRecords;
    }
}
