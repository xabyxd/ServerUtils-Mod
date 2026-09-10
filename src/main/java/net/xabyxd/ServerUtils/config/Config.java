package net.xabyxd.ServerUtils.config;

import java.io.File;

import net.minecraftforge.common.config.Configuration;
import net.xabyxd.ServerUtils.Serverutils;

public class Config {

    // Config file directory and file generation
    public static File configDir = new File("config", Serverutils.MODID);
    public static File configFile = new File(configDir, Serverutils.MODID + ".cfg");

    // TEST CONFIG GENERATION
    public static String configGenerationTest = "Config loaded correctly!";

    // Config mod options
    public static boolean logDimensionChanges = true;
    public static boolean enableVersionChecker = true;
    public static String REMOTE_VERSION_URL = "https://xabyserver.ddns.net/ServerUtils/version.txt";

    public static void synchronizeConfiguration() {
        if (!configFile.getParentFile().exists()) {
            configFile.getParentFile().mkdirs();
        }

        Configuration configuration = new Configuration(configFile);

        REMOTE_VERSION_URL = configuration.getString(
            "REMOTE_VERSION_URL",
            Configuration.CATEGORY_GENERAL,
            REMOTE_VERSION_URL,
            "URL to check for updates, just in case domain name changes."
        );

        enableVersionChecker = configuration.getBoolean(
            "enableVersionChecker",
            Configuration.CATEGORY_GENERAL,
            enableVersionChecker,
            "Enable or disable the version checker."
        );

        configGenerationTest = configuration.getString(
            "configGenerationTest",
            Configuration.CATEGORY_GENERAL,
            configGenerationTest,
            "Simple test for config generation."
        );

        logDimensionChanges = configuration.getBoolean(
            "logDimensionChanges",
            Configuration.CATEGORY_GENERAL,
            true,
            "Should Server Utils log dimension changes in the server console?"
        );

        if (configuration.hasChanged()) {
            configuration.save();
        }
    }
}