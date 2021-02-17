package de.powh.utils;

import java.io.File;

public enum OSHelper {

    WINDOWS("AppData" + File.separator + "Roaming" + File.separator + ".statstracker"),
    MAC("Library" + File.separator + "Application Support" + File.separator + "statstracker"),
    LINUX(".statstracker");


    private final String dataDir;
    private OSHelper(String dataDir) {
        this.dataDir = File.separator + dataDir + File.separator;
    }

    public String getDataDir() {
        return System.getProperty("user.home") + dataDir;
    }

    public static final OSHelper getOS() {
        final String currentOS = System.getProperty("os.name").toLowerCase();
        if(currentOS.startsWith("windows")) {
            return WINDOWS;
        }
        else if(currentOS.startsWith("mac")) {
            return MAC;
        }
        else {
            return LINUX;
        }
    }
}
