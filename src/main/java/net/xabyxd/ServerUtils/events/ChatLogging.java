package net.xabyxd.ServerUtils.events;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.event.ServerChatEvent;
import net.xabyxd.ServerUtils.config.Config;
import net.xabyxd.ServerUtils.utils.FileLogger;
import net.xabyxd.ServerUtils.utils.LogHelper;

public class ChatLogging {
    
    @SubscribeEvent
    public void onServerChat(ServerChatEvent event) {
        logChat("<" + event.username + "> " + event.message);
    }

    // Server chat logging
    public static synchronized void logChat(String message) {
    if (!Config.enableChatLogging) return;
        LogHelper.info(message);
        FileLogger.append(Config.logFile, message);
    }
}