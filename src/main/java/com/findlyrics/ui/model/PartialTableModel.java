package com.findlyrics.ui.model;

import com.findlyrics.db.dao.BlindService;
import com.findlyrics.db.service.impl.DBLyricsService;
import com.findlyrics.exceptions.DataConnectionException;

import javax.swing.table.AbstractTableModel;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Padonag on 14.09.2014.
 */
public class PartialTableModel extends AbstractTableModel implements ITableModelPagination{
    private static final String[] namesOfColumns = {"Artist", "Song name", "Lyrics"};
    private static final int RECORDS_PER_PAGE = 20;

    private int currentPage = 1;
    private int noOfPages;
    private int offset;
    private List<String[]> pageData;
    private DBLyricsService service;



    public PartialTableModel(DBLyricsService service) throws DataConnectionException {
        this.service = service;
        this.pageData = createPageData(service.getDTO(currentPage, RECORDS_PER_PAGE));
        this.noOfPages = service.getNumberOfPages();


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
            offset += RECORDS_PER_PAGE;
            pageData = createPageData(service.getDTO(currentPage, RECORDS_PER_PAGE));
        }

    }

    public void previousPage() throws DataConnectionException {
        if (currentPage > 0) {
            currentPage--;
            offset -= RECORDS_PER_PAGE;
            pageData = createPageData(service.getDTO(currentPage, RECORDS_PER_PAGE));
        }

    }

    @Override
    public void refreshData() {
        this.fireTableDataChanged();
    }

    private List<String[]> createPageData(LyricsDTO dto) {
        List<String[]> pageData = new LinkedList<String[]>();
        List<LyricItemDTO> itemDTOs = dto.getSearchResults();

        for (LyricItemDTO currentDTO : itemDTOs) {
            pageData.add(new String[]{currentDTO.getArtistName(), currentDTO.getSongName(), currentDTO.getLyrics()});
        }
        return pageData;
    }

    public int getPageCount() {
        return noOfPages;
    }
}
