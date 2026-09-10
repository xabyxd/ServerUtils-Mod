package net.xabyxd.ServerUtils.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import cpw.mods.fml.relauncher.FMLInjectionData;
import net.xabyxd.ServerUtils.Serverutils;
import net.xabyxd.ServerUtils.config.Config;

public class VersionChecker implements Runnable {

    public static final String REMOTE_VERSION_URL = Config.REMOTE_VERSION_URL;

    public static final String LOCAL_VERSION = Serverutils.VERSION;

    private static String latestVersion = "";
    private static boolean updateAvailable;
    private static boolean noConnection;

    public static void startCheck() {
        Thread thread = new Thread(
            new VersionChecker(),
            "ServerUtils-VersionChecker"
        );

        thread.setDaemon(true);
        thread.start();
    }

    @Override
    public void run() {
        if (!Config.enableVersionChecker) return;
        String minecraftVersion = (String) FMLInjectionData.data()[4];

        String prefix = "[" + minecraftVersion + "]=";

        try {
            URL url = new URL(REMOTE_VERSION_URL);
            BufferedReader reader =
                new BufferedReader(
                    new InputStreamReader(
                        url.openStream()
                    )
                );

            String line;

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.startsWith(prefix)) {
                    latestVersion = line.substring(prefix.length()).trim();
                    break;
                }
            }

            reader.close();

            if (latestVersion.isEmpty()) {
                Serverutils.LOGGER.warn(
                    "No version information found for Minecraft {}",
                    minecraftVersion
                );
                return;
            }

            if (LOCAL_VERSION.equals(latestVersion)) {
                Serverutils.LOGGER.info(
                    "ServerUtils is up to date! ({})",
                    LOCAL_VERSION
                );
            } else {
                updateAvailable = true;
                Serverutils.LOGGER.warn(
                    "A new version of ServerUtils is available!"
                );
                Serverutils.LOGGER.warn(
                    "Current version: {}",
                    LOCAL_VERSION
                );
                Serverutils.LOGGER.warn(
                    "Latest version: {}",
                    latestVersion
                );
            }
        } catch (Exception e) {
            noConnection = true;
            Serverutils.LOGGER.warn(
                    "Unable to check for ServerUtils updates."
            );
            Serverutils.LOGGER.debug(
                "Version check exception:",
                e
            );
        }
    }

    public static boolean isUpdateAvailable() {
        return updateAvailable;
    }

    public static boolean hasNoConnection() {
        return noConnection;
    }

    public static String getLatestVersion() {
        return latestVersion;
    }
}