package com.findlyrics.ui.view.panels;

import com.findlyrics.ui.model.listmodel.ArrayListModel;
import com.findlyrics.ui.model.listmodel.ListItem;
import com.findlyrics.ui.model.listmodel.SongListRenderer;

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
        contentList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                clickButtonAt(e.getPoint());
            }
        });
        scrollPane = new JScrollPane(contentList);
        add(scrollPane);
    }

    private void clickButtonAt(Point point) {
        int index = contentList.locationToIndex(point);
        ListItem item = contentList.getModel().getElementAt(index);
        item.getButton().doClick();
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

    public void setNumberOfFound() {
        numberOfFound.setText(getNumber());
    }

}
