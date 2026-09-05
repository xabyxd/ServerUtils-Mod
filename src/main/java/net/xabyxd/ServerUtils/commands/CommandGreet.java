package net.xabyxd.ServerUtils.commands;

import java.util.List;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class CommandGreet extends CommandBase {
    
    @Override
    public String getCommandName() {
        return "greet";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/greet <player>";
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args) {
        if (args.length == 1) {
            return getListOfStringsMatchingLastWord(
                args, MinecraftServer.getServer().getConfigurationManager().getAllUsernames()
            );
        }
        return null;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if (args.length == 0) {
            sender.addChatMessage(
                new ChatComponentText(EnumChatFormatting.YELLOW + "[INFO] " + EnumChatFormatting.WHITE + "Please specify a player.")
            );
            return;
        }
        
        String playerName = args[0];

        if (playerName.equals("everyone")) {
            MinecraftServer.getServer().getConfigurationManager().sendChatMsg(
                new ChatComponentText(EnumChatFormatting.GREEN + "[SERVER] " + EnumChatFormatting.WHITE + "Hello everyone!!")
            );
            sender.addChatMessage(
                new ChatComponentText(EnumChatFormatting.YELLOW + "[INFO] " + EnumChatFormatting.WHITE + "Message sent to everyone.")
            );
            return;
        }

        EntityPlayer target = MinecraftServer.getServer().getConfigurationManager().func_152612_a(playerName);

        if (target == null) {
            sender.addChatMessage(
                new ChatComponentText(EnumChatFormatting.YELLOW + "[INFO] " + EnumChatFormatting.WHITE + "Player: " + playerName + " not found or (offline?).")
            );
            return;
        }

        target.addChatMessage(
            new ChatComponentText(EnumChatFormatting.GREEN + "[SERVER] " + EnumChatFormatting.WHITE + "Hello " + playerName + "!")
        );
        
        sender.addChatMessage(
            new ChatComponentText(EnumChatFormatting.YELLOW + "[INFO] " + EnumChatFormatting.WHITE + "Message sent to: " + playerName + ".")
        );
    }
}