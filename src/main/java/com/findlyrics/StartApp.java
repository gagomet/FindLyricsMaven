package com.findlyrics;

import com.findlyrics.ui.controller.UiController;
import com.findlyrics.ui.mediator.IMediator;
import com.findlyrics.ui.mediator.impl.ButtonsMediator;
import com.findlyrics.ui.model.UiModel;
import com.findlyrics.ui.view.UiViewer;
import com.findlyrics.util.ArgsUtil;

import javax.swing.SwingUtilities;

/**
 * Created by Padonag on 21.07.2014.
 */
public class StartApp {

    public static void main(String[] args) {
        if (args.length == 0) {
            throw new IllegalArgumentException("Must specify arguments!");
        }

        ArgsUtil.setParameters(args[0]);

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                IMediator mediator = new ButtonsMediator();
                UiModel model = new UiModel(mediator);
                UiViewer view = new UiViewer(model);
                UiController controller = new UiController(model, view);
                view.setVisible(true);
            }
        });
    }
}



