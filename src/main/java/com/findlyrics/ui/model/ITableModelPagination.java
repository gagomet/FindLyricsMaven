package com.findlyrics.ui.model;

import com.findlyrics.exceptions.DataConnectionException;

/**
 * Created by Padonag on 17.09.2014.
 */
public interface ITableModelPagination {
    public void nextPage() throws DataConnectionException;

    public void previousPage() throws DataConnectionException;

    public int getPageCount();

    public void refreshData();

}
