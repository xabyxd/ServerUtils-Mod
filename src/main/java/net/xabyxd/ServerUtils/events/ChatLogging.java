package net.xabyxd.ServerUtils.events;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.event.ServerChatEvent;
import net.xabyxd.ServerUtils.config.Config;
import net.xabyxd.ServerUtils.utils.LogHelper;

public class ChatLogging {

    private static final SimpleDateFormat TIMESTAMP_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @SubscribeEvent
    public void onServerChat(ServerChatEvent event) {
        logChat("<" + event.username + "> " + event.message);
    }

    // Server chat logging
    public static synchronized void logChat(String message) {
        if (!Config.enableChatLogging) return;

        try {
            if (Config.logFile.getParentFile() != null && !Config.logFile.getParentFile().exists()) {
                Config.logFile.getParentFile().mkdirs();
            }

            try (PrintWriter writer = new PrintWriter(new FileWriter(Config.logFile, true))) {
                writer.println("[" + TIMESTAMP_FORMAT.format(new Date()) + "] " + message);
            }
        } catch (IOException e) {
            LogHelper.info("Failed to write chat log: " + e.getMessage());
            LogHelper.debug("Chat logging exception:", e);
        }
    }
}