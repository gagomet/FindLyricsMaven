package main.java.com.findlyrics;

import main.java.com.findlyrics.ui.controller.UiController;
import main.java.com.findlyrics.ui.model.UiModel;
import main.java.com.findlyrics.ui.view.UiViewer;
import main.java.com.findlyrics.util.ArgsUtil;

import javax.swing.*;

/**
 * Created by Padonag on 21.07.2014.
 */
public class StartApp {

    public static void main(String[] args) {
        ArgsUtil.setParameters(args[0]);

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                UiModel model = new UiModel();
                UiViewer view = new UiViewer(model);
                UiController controller = new UiController(model, view);
                view.setVisible(true);
            }
        });
    }
}



