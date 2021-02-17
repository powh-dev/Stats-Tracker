package de.powh.ui;

import de.powh.ui.ApplicationMain;
import javafx.scene.control.ButtonBar;

public class WindowDraggable {

    private double xOffset = 0;
    private double yOffset = 0;

    public WindowDraggable(ButtonBar buttonBar) {
        buttonBar.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        buttonBar.setOnMouseDragged(event -> {
            ApplicationMain.getStage().setX(event.getScreenX() - xOffset);
            ApplicationMain.getStage().setY(event.getScreenY() - yOffset);
        });
    }

}
