package de.powh.utils;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class TimeUtil {

    public static void runLater(Runnable runnable, int delay) {
        runLater(runnable, delay, TimeUnit.MILLISECONDS);
    }

    public static void runLater(Runnable runnable, int delay, TimeUnit unit) {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                runnable.run();
            }
        }, unit.toMillis(delay));
    }

}
