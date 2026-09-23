package net.xabyxd.ServerUtils.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.xabyxd.ServerUtils.config.Config;

public class CommandInfo extends CommandBase {
    
    @Override
    public String getCommandName() {
        return "info";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/info";
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    } // allow everyone to use this command

    @Override
    public void processCommand(ICommandSender sender, String[] args) {

        for (String line : Config.commandInfo) {// FIXED: /info command now sends the result to de command sender (player)
            sender.addChatMessage(
                new ChatComponentText(EnumChatFormatting.GRAY + line)
            );
        }
        return;
    }
}