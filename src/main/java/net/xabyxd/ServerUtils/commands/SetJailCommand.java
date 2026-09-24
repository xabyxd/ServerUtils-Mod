package net.xabyxd.ServerUtils.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.xabyxd.ServerUtils.utils.JailManager;

public class SetJailCommand extends CommandBase {

    @Override
    public String getCommandName() {
        return "setjail";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/setjail <x> <y> <z>";
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return sender.canCommandSenderUseCommand(4, this.getCommandName());
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if (args.length < 3) {
            sender.addChatMessage(
                new ChatComponentText(EnumChatFormatting.RED + "Usage: /setjail <x> <y> <z>")
            );
            return;
        }

        double x, y, z;
        try {
            x = Double.parseDouble(args[0]);
            y = Double.parseDouble(args[1]);
            z = Double.parseDouble(args[2]);
        } catch (NumberFormatException e) {
            sender.addChatMessage(
                new ChatComponentText(EnumChatFormatting.RED + "Coordinates must be numbers.")
            );
            return;
        }

        JailManager.setJailLocation(x, y, z);

        sender.addChatMessage(
            new ChatComponentText(EnumChatFormatting.YELLOW + "[INFO] " + EnumChatFormatting.WHITE
                + "Jail location set to (" + x + ", " + y + ", " + z + ").")
        );
    }
}