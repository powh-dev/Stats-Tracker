package de.powh.ui;


import javafx.application.Platform;
import javafx.scene.control.Label;

import java.util.Timer;
import java.util.TimerTask;


public class LabelEdit {

    private double opacity;
    private Timer fadeInTimer;
    private Timer fadeOutTimer;

    public void fadeEditText(Label label, String newText) {
        opacity = label.getOpacity();
        fadeInTimer = new Timer();
        fadeOutTimer = new Timer();
        fadeInTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if(opacity <= 0) {
                    Platform.runLater(() -> label.setText(newText));
                    fadeInTimer.cancel();
                    fadeOutTimer.scheduleAtFixedRate(new TimerTask() {
                        @Override
                        public void run() {
                            if(opacity >= 1) {
                                fadeOutTimer.cancel();
                            }
                            opacity = opacity + 0.05;
                            label.setOpacity(opacity);
                        }
                    }, 100, 50);
                }
                opacity = opacity - 0.05;
                label.setOpacity(opacity);
            }
        }, 100, 50);
    }

    public void fadeOut(Label label) {
        opacity = label.getOpacity();
        fadeOutTimer = new Timer();
        fadeOutTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if(opacity <= 0) {
                    label.setVisible(false);
                    label.setOpacity(1);
                    fadeOutTimer.cancel();
                }
                opacity = opacity - 0.05;
                label.setOpacity(opacity);
            }
        }, 100, 100);
    }

    public void fadeIn(Label label) {
        opacity = 0;
        label.setOpacity(opacity);
        label.setVisible(true);
        fadeInTimer = new Timer();
        fadeInTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if(opacity >= 1) {
                    fadeInTimer.cancel();
                }
                opacity = opacity + 0.05;
                label.setOpacity(opacity);
            }
        }, 100, 100);
    }

}
