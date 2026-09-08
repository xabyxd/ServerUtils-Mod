package net.xabyxd.ServerUtils.config;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

public class Config {

    // TEST CONFIG GENERATION
    public static String configGenerationTest = "Config loaded correctly!";
    public static boolean logDimensionChanges = true;

    public static void synchronizeConfiguration(File configFile) {
        Configuration configuration = new Configuration(configFile);

        configGenerationTest = configuration.getString("configGenerationTest", Configuration.CATEGORY_GENERAL, configGenerationTest, "Simple test for config generation.");

        logDimensionChanges = configuration.getBoolean("logDimensionChanges", Configuration.CATEGORY_GENERAL, true, "Should Server Utils log dimension changes in the server console?");

        if (configuration.hasChanged()) {
            configuration.save();
        }
    }
}