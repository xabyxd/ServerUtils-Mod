package net.xabyxd.ServerUtils.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.xabyxd.ServerUtils.config.Config;
import net.xabyxd.ServerUtils.utils.LogHelper;

public class CommandReload extends CommandBase {

    @Override
    public String getCommandName() {
        return "sureload";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/sureload";
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return sender.canCommandSenderUseCommand(4, this.getCommandName());
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        try {
            Config.synchronizeConfiguration();
        } catch (Exception e) {
            LogHelper.error("Error reloading config", e);
            sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Error reloading config: " + e.getMessage()));
            return;
        }
        sender.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "ServerUtils config reloaded."));
    }
}