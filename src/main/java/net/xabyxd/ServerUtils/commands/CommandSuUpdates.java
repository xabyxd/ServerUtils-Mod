package net.xabyxd.ServerUtils.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.xabyxd.ServerUtils.utils.VersionChecker;

public class CommandSuUpdates extends CommandBase {

    @Override
    public String getCommandName() {
        return "suupdates";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/suupdates";
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return sender.canCommandSenderUseCommand(4, getCommandName());
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        sender.addChatMessage(new ChatComponentText(
            EnumChatFormatting.GRAY + "Checking for ServerUtils updates..."
        ));

        VersionChecker.startCheck(sender);
    }
}