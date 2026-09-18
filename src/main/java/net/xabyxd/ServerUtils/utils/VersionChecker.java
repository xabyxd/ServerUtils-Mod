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

    public enum UpdateType {
        NONE,
        PATCH,
        MINOR,
        MAJOR
    }

    private static String latestVersion = "";
    private static boolean updateAvailable;
    private static boolean noConnection;
    private static UpdateType updateType = UpdateType.NONE;

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
                LogHelper.warn(
                    "No version information found for Minecraft {}",
                    minecraftVersion
                );
                return;
            }

            if (LOCAL_VERSION.equals(latestVersion)) {
                updateType = UpdateType.NONE;
                LogHelper.info(
                    "ServerUtils is up to date! ({})",
                    LOCAL_VERSION
                );
            } else {
                updateAvailable = true;
                updateType = compareVersions(LOCAL_VERSION, latestVersion);

                LogHelper.warn(
                    "A new {} update of ServerUtils is available!",
                    updateType.name().toLowerCase()
                );
                LogHelper.warn(
                    "Current version: {}",
                    LOCAL_VERSION
                );
                LogHelper.warn(
                    "Latest version: {}",
                    latestVersion
                );
            }
        } catch (Exception e) {
            noConnection = true;
            LogHelper.warn(
                "Unable to check for ServerUtils updates."
            );
            LogHelper.debug(
                "Version check exception:",
                e
            );
        }
    }

    /**
     * Compares two "major.minor.patch" version strings and returns which
     * kind of update "remoteVersion" represents relative to "localVersion".
     * Missing segments are treated as 0 (e.g. "1.2" == "1.2.0").
     */
    private static UpdateType compareVersions(String localVersion, String remoteVersion) {
        int[] local = parseVersion(localVersion);
        int[] remote = parseVersion(remoteVersion);

        if (remote[0] != local[0]) {
            return UpdateType.MAJOR;
        }

        if (remote[1] != local[1]) {
            return UpdateType.MINOR;
        }

        if (remote[2] != local[2]) {
            return UpdateType.PATCH;
        }

        return UpdateType.NONE;
    }

    /**
     * Parses a version string into a 3-element [major, minor, patch] array.
     * Non-numeric suffixes (e.g. "1.2.3-beta") are stripped before parsing,
     * and missing segments default to 0.
     */
    private static int[] parseVersion(String version) {
        int[] parts = new int[]{0, 0, 0};
        String[] segments = version.split("\\.");

        for (int i = 0; i < segments.length && i < parts.length; i++) {
            String segment = segments[i].replaceAll("[^0-9].*", "");
            if (!segment.isEmpty()) {
                try {
                    parts[i] = Integer.parseInt(segment);
                } catch (NumberFormatException e) {
                    parts[i] = 0;
                }
            }
        }

        return parts;
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

    public static UpdateType getUpdateType() {
        return updateType;
    }
}