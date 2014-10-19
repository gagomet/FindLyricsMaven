package com.findlyrics;

import com.findlyrics.ui.controller.ListController;
import com.findlyrics.ui.mediator.IMediator;
import com.findlyrics.ui.mediator.impl.ButtonsMediator;
import com.findlyrics.ui.model.UiModel;
import com.findlyrics.ui.view.ListModelView;
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
//                UiViewer view = new UiViewer(model);
//                UiController controller = new UiController(model, view);

                ListModelView view = new ListModelView(model);
                ListController controller = new ListController(model, view);

                view.setVisible(true);
            }
        });
    }
}



