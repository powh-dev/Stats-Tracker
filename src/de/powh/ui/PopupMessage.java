package de.powh.ui;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Popup;
import javafx.stage.Stage;
import de.powh.utils.TimeUtil;


public class PopupMessage {

    public static Popup createPopup(final String message) {
        final Popup popup = new Popup();
        popup.setAutoFix(true);
        popup.setAutoHide(true);
        popup.setHideOnEscape(true);
        Label label = new Label(message);
        label.setText(message);
        label.setTextFill(Color.WHITE);
        label.setOnMouseReleased(event -> popup.hide());
        label.getStylesheets().add(PopupMessage.class.getResource("../assets/styles.css").toExternalForm());
        label.getStyleClass().add("popup");
        popup.getContent().add(label);
        return popup;
    }

    public static void display(final String message, final Stage stage) {
        display(message, stage, -99999, -99999);
    }
    public static void display(final String message, final Stage stage, double x, double y) {
        final Popup popup = createPopup(message);
        popup.setOnShown(event -> {
            if(x == -99999 && y == -99999) {
                popup.setX(stage.getX() + stage.getWidth()/2 - popup.getWidth()/2);
                popup.setY(stage.getY() + stage.getHeight()/2 - popup.getHeight()/2);
            } else {
                popup.setX(x);
                popup.setY(y);
            }
        });
        popup.show(stage);
        TimeUtil.runLater(() -> Platform.runLater(popup::hide), 1500);
    }

}
