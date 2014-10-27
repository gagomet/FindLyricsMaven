package com.findlyrics.ui.view.panels;

import com.findlyrics.ui.controller.listeners.ContentPaneMouseListener;
import com.findlyrics.ui.model.listmodel.ArrayListModel;
import com.findlyrics.ui.model.listmodel.ListItem;
import com.findlyrics.ui.model.listmodel.SongListRenderer;
import com.findlyrics.ui.view.ListModelView;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by Padonag on 12.10.2014.
 */
public class ListContentPanel extends JPanel {

    private JList<ListItem> contentList;
    private JLabel numberOfFound;
    private JScrollPane scrollPane;

    public ListContentPanel() {
        setLayout(new FlowLayout());
        numberOfFound = new JLabel();
        add(numberOfFound);
        contentList = new JList<ListItem>();
        contentList.setCellRenderer(new SongListRenderer());
        contentList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        scrollPane = new JScrollPane(contentList);
        add(scrollPane);
    }

    public void setMouseListener(MouseAdapter adapter){
        this.contentList.addMouseListener(adapter);
    }

    private String getNumber() {
        StringBuilder builder = new StringBuilder();
        if (contentList.getModel().getSize() == 0) {
            builder.append("Sorry, there is no songs which relevant to your query :( ");
            return builder.toString();
        } else {
            builder.append("We found ");
            builder.append(contentList.getModel().getSize());
            builder.append(" songs for your query");
            return builder.toString();
        }
    }

    public void setListModel(ArrayListModel model) {
        contentList.setModel(model);
    }

    public JScrollPane getScrollPane() {
        return scrollPane;
    }

    public JList<ListItem> getContentList() {
        return contentList;
    }

    public void setNumberOfFound() {
        numberOfFound.setText(getNumber());
    }

}
