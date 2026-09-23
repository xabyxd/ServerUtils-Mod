package net.xabyxd.ServerUtils.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class JailCommand extends CommandBase {
    
    @Override
    public String getCommandName() {
        return "jail";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/jail <player name|player UUID> <reason> <time>";
    }

    //@Override
    //public String getCommandHelp(ICommandSender sender) {
    //    return "Jails a player on the server jail for a specified amount of time.";
    //} FUTURE FEATURE

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return sender.canCommandSenderUseCommand(4, this.getCommandName());
    }

    @Override // (Test implementation)
    public void processCommand(ICommandSender sender, String[] args) {
        if (args.length < 3) {
            sender.addChatMessage(
                new ChatComponentText(EnumChatFormatting.RED + "Usage: /jail <player name|player UUID> <reason> <time>")
            );
            return;
        }
        
        String playerName = args[0];
        String reason = args[1];
        int time;
        
        try {
            time = Integer.parseInt(args[2]);
        } catch (NumberFormatException e) {
            sender.addChatMessage(
                new ChatComponentText(EnumChatFormatting.RED + "The time must be integer in seconds.")
            );
            return;
        }

        sender.addChatMessage(
            new ChatComponentText(EnumChatFormatting.YELLOW + "[INFO] " + EnumChatFormatting.WHITE + "Jailing player: " + playerName + " for " + time + " seconds with reason: " + reason + ".")
        );
    }
}
