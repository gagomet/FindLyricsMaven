package main.java.com.findlyrics;

import com.findlyrics.ui.controller.UiController;
import com.findlyrics.ui.model.UiModel;
import com.findlyrics.ui.view.UiViewer;
import com.findlyrics.util.ArgsUtil;

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



