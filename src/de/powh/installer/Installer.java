package de.powh.installer;

import de.powh.ui.ApplicationMain;
import de.powh.utils.OSHelper;

import java.io.*;

public class Installer {

    private static String BASE_URL =  "https://raw.githubusercontent.com/powh-dev/Stats-Tracker/versions/";
    private static String version = null;

    public static boolean outdatedVersion() {
        System.out.println(getVersion());
        System.out.println(new File(OSHelper.getOS().getDataDir() + "StatsTracker-" + getVersion() + ".jar").exists());
        return !new File(OSHelper.getOS().getDataDir() + "StatsTracker-" + getVersion() + ".jar").exists();
    }

    public static String getVersion() {
        if(version == null) {
            try{
                InputStream stream = FileHelper.getStreamFromUrl(BASE_URL + "latest.txt");
                InputStreamReader reader = new InputStreamReader(stream);
                BufferedReader buffReader = new BufferedReader(reader);
                version = buffReader.readLine();
                buffReader.close();
                reader.close();
                stream.close();

            } catch(IOException e) {
                e.printStackTrace();
                ApplicationMain.kill(e);
            }
        }

        return version;
    }

    public static InputStream downloadJar() throws IOException {
        return FileHelper.getStreamFromUrl(BASE_URL + getVersion() + "/StatsTracker.jar");
    }

}
