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

    public static void synchronizeConfiguration() {
        if (!configFile.getParentFile().exists()) {
            configFile.getParentFile().mkdirs();
        }

        Configuration configuration = new Configuration(configFile);

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