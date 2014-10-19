package com.findlyrics.ui.model.listmodel;

import javax.swing.DefaultListSelectionModel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.util.Observable;

/**
 * Created by Padonag on 09.10.2014.
 */
public class SongListItemSelection<E> extends Observable {

    private ArrayListModel<E> arrayListModel = new ArrayListModel<E>();
    private ListSelectionModel listSelectionModel = new DefaultListSelectionModel();
    private ListSelectionAdapter listSelectionAdapter = new ListSelectionAdapter();

    public SongListItemSelection() {
        listSelectionModel.addListSelectionListener(listSelectionAdapter);
    }

    public void setListModels(ArrayListModel<E> listModel, ListSelectionModel listSelectionModel) {
        this.arrayListModel = listModel;
        if (this.listSelectionModel != null) {
            this.listSelectionModel
                    .removeListSelectionListener(listSelectionAdapter);
        }
        this.listSelectionModel = listSelectionModel;
        if (listSelectionModel != null) {
            listSelectionModel.addListSelectionListener(listSelectionAdapter);
        }
    }

    public E getSelection() {
        int minSelectionNumber = listSelectionModel.getMinSelectionIndex();
        E elementAt = null;
        if (minSelectionNumber > -1) {
            elementAt = arrayListModel.getElementAt(minSelectionNumber);
        }
        return elementAt;
    }

    public void setSelection(E selection) {
        int indexOf = arrayListModel.indexOf(selection);
        listSelectionModel.setSelectionInterval(indexOf, indexOf);
    }

    private class ListSelectionAdapter implements ListSelectionListener {

        public void valueChanged(ListSelectionEvent e) {
            setChanged();
            notifyObservers();
        }

    }
}
