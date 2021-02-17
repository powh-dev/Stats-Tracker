package de.powh.ui;

import de.powh.installer.FileHelper;
import de.powh.installer.Installer;
import de.powh.utils.OSHelper;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class ApplicationMain extends Application {

    private static Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("scenes/updater/updater.fxml"));
            primaryStage.setTitle("Stats Tracker Launcher");
            primaryStage.getIcons().add(new Image(ClassLoader.getSystemResourceAsStream("assets/icon.png")));
            primaryStage.setScene(new Scene(root, 500, 300));
            primaryStage.setResizable(false);
            primaryStage.initStyle(StageStyle.UNDECORATED);
            stage = primaryStage;
            primaryStage.show();
            Label status = (Label)root.lookup("#progressMessage");
            File dataDir = new File(OSHelper.getOS().getDataDir());
            if(!dataDir.exists()) {
                dataDir.mkdirs();
            }
            int delay = 1500;

            if(Installer.outdatedVersion()) {
                delay = 3000;
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Platform.runLater(() ->  status.setText("Downloading Stats Tracker v" + Installer.getVersion() + "..."));
                    }
                }, 1000);

                for (File file : dataDir.listFiles()) {
                    if(file.getName().startsWith("StatsTracker-") && file.getName().endsWith(".jar")) {
                        file.deleteOnExit();
                    }
                }

                FileHelper.writeFile(Installer.downloadJar(), new File(dataDir + "/StatsTracker-" + Installer.getVersion() + ".jar"));

                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Platform.runLater(() ->  status.setText("Download Complete!"));
                    }
                }, 2000);
            }
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    try {
                        Runtime.getRuntime().exec("java -jar " + OSHelper.getOS().getDataDir() + "StatsTracker-" + Installer.getVersion() + ".jar");
                        exit();
                    } catch (IOException exception) {
                        exception.printStackTrace();
                        kill(exception);
                    }
                }
            }, delay);
        } catch (Exception exception) {
            exception.printStackTrace();
            kill(exception);
        }
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

    public static Stage getStage() {
        return stage;
    }

    public static void setStage(Stage stage) {
        ApplicationMain.stage = stage;
        return;
    }

    public static void exit() {
        System.exit(0);
    }

    public static void kill(Exception e) {
        JOptionPane.showMessageDialog(null, e.toString().split("\nat ")[0], "An Error occurred", JOptionPane.ERROR_MESSAGE);
        DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy HH-mm-ss");
        File crashlogDir = new File(OSHelper.getOS().getDataDir() + "crash-logs/");
        if(!crashlogDir.exists()) {
            crashlogDir.mkdirs();
        }
        File exceptionFile = new File(OSHelper.getOS().getDataDir() + "crash-logs/Error-" + dateFormat.format(new Date()) + ".txt");
        PrintWriter pw;
        try {
            pw = new PrintWriter(exceptionFile);
            e.printStackTrace(pw);
            pw.close();
            exceptionFile.createNewFile();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        exit();
    }

}
