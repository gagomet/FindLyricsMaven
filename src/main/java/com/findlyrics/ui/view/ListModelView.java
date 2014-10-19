package com.findlyrics.ui.view;

import com.findlyrics.ui.model.UiModel;
import com.findlyrics.ui.view.panels.ListContentPanel;
import com.findlyrics.ui.view.panels.NextSearchPanel;
import com.findlyrics.ui.view.panels.QueryPanel;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;
import java.awt.Dimension;

/**
 * Created by Padonag on 12.10.2014.
 */
public class ListModelView extends JFrame {

    private static final int FRAME_WIDTH = 800;
    private static final int FRAME_HEIGHT = 600;

    private QueryPanel queryPanel;
    private ListContentPanel contentPanel;
    private NextSearchPanel nextSearchPanel;
    private UiModel model;

    public ListModelView(UiModel model) {
        this.model = model;
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        queryPanel = new QueryPanel(this);
        contentPanel = new ListContentPanel();
        nextSearchPanel = new NextSearchPanel();
        add(queryPanel);
        add(contentPanel);
        add(nextSearchPanel);
        contentPanel.setVisible(false);
        nextSearchPanel.setVisible(false);
        contentPanel.getScrollPane().setPreferredSize(new Dimension(FRAME_WIDTH - 50, FRAME_HEIGHT - 200));
        pack();
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setResizable(false);
    }

    public QueryPanel getQueryPanel() {
        return queryPanel;
    }

    public void showError(String errorMessage) {
        JOptionPane.showMessageDialog(null, errorMessage);
    }

    public ListContentPanel getContentPanel() {
        return contentPanel;
    }

    public NextSearchPanel getNextSearchPanel() {
        return nextSearchPanel;
    }

}
