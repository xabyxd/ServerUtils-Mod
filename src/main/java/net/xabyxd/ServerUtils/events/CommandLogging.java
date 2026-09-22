package net.xabyxd.ServerUtils.events;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.command.ICommandSender;
import net.minecraftforge.event.CommandEvent;
import net.xabyxd.ServerUtils.config.Config;
import net.xabyxd.ServerUtils.utils.FileLogger;
import net.xabyxd.ServerUtils.utils.LogHelper;

public class CommandLogging {

    @SubscribeEvent
    public void onCommand(CommandEvent event) {
        if (!Config.enableCommandLogging) return;

        ICommandSender sender = event.sender;
        String args = event.parameters.length > 0 ? String.join(" ", event.parameters) : "";

        String message = sender.getCommandSenderName()
            + " ran: /" + event.command.getCommandName()
            + (args.isEmpty() ? "" : " " + args);

        LogHelper.info(message);
        FileLogger.append(Config.commandLogFile, message);
    }
}