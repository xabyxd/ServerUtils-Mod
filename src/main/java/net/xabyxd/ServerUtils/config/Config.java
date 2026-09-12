package net.xabyxd.ServerUtils.config;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraftforge.common.config.Configuration;
import net.xabyxd.ServerUtils.Serverutils;

public class Config {

    // Config file directory and file generation
    public static File configDir = new File("config", Serverutils.MODID);
    public static File configFile = new File(configDir, Serverutils.MODID + ".cfg");

    // Category config
    public static final String CATEGORY_WELCOME = "welcome messages";
    public static final String CATEGORY_VERSION_CHECKER = "version checker";
    public static final String CATEGORY_COMMANDS = "commands";

    // Config mod options
    public static boolean logDimensionChanges = true;
    public static boolean enableVersionChecker = true;
    public static String REMOTE_VERSION_URL = "https://xabyserver.ddns.net/ServerUtils/version.txt";

    // Welcome message config
    public static String normalUserWelcomeMessage = "Welcome to the server!, ";
    public static String opUserWelcomeMessage = "Welcome back, ";

    // Commands config
    public static List<String> commandInfo = new ArrayList<>(
        Arrays.asList(
            "This server is running Server Utils v" + Serverutils.VERSION,
            "The mod is developed by xabyxd",
            "Sincerely, the mod author",
            "Whathever"
        )
    );

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
            CATEGORY_VERSION_CHECKER,
            enableVersionChecker,
            "Enable or disable the version checker."
        );

        logDimensionChanges = configuration.getBoolean(
            "logDimensionChanges",
            Configuration.CATEGORY_GENERAL,
            true,
            "Should Server Utils log dimension changes in the server console?"
        );

        normalUserWelcomeMessage = configuration.getString(
            "normalUserWelcomeMessage",
            CATEGORY_WELCOME,
            normalUserWelcomeMessage,
            "The message that is sent to normal users when they join the server."
        );

        opUserWelcomeMessage = configuration.getString(
            "opUserWelcomeMessage",
            CATEGORY_WELCOME,
            opUserWelcomeMessage,
            "The message that is sent to OP users when they join the server."
        );

        commandInfo = new ArrayList<>(
            Arrays.asList(
                configuration.getStringList(
                    "commandInfo",
                    CATEGORY_COMMANDS,
                    commandInfo.toArray(new String[0]),
                    "Lines shown by the /info command, one entry per line."
                )
            )
        );

        if (configuration.hasChanged()) {
            configuration.save();
        }
    }
}