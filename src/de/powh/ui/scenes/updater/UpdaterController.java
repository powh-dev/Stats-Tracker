package de.powh.ui.scenes.updater;

import de.powh.ui.ApplicationMain;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import de.powh.ui.PopupMessage;
import de.powh.ui.WindowDraggable;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.net.URL;
import java.util.ResourceBundle;

public class UpdaterController {
    @FXML
    private ImageView exitBtn;
    @FXML
    private ImageView maximizeBtn;
    @FXML
    private ImageView minimizeBtn;
    @FXML
    private ButtonBar titleBar;
    @FXML
    private Label authorLabel;
    @FXML
    private Label progressLabel;
    @FXML
    private Tooltip tooltip;

    @FXML
    private URL location;

    @FXML
    private ResourceBundle resources;

    public UpdaterController() {
    }

    @FXML
    private void initialize() {
        new WindowDraggable(titleBar);
    }

    @FXML
    private void onClickAuthorLabel() {
        StringSelection selection = new StringSelection("powh#9119");
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selection, selection);
        Stage stage = ApplicationMain.getStage();
        Point point = MouseInfo.getPointerInfo().getLocation();
        PopupMessage.display("Copied to Clipboard!", stage, point.getX() + 10, point.getY() + 10);
    }

    @FXML
    private void buttonClick(MouseEvent event) {
        if (event.getTarget() == exitBtn) {
            ApplicationMain.exit();
        }
        if (event.getTarget() == minimizeBtn) {
            ApplicationMain.getStage().setIconified(true);
        }
        if (event.getTarget() == maximizeBtn) {
            ApplicationMain.getStage().setMaximized(!ApplicationMain.getStage().isMaximized());
        }
    }

    public static void setMessage(String message) {

    }

}
