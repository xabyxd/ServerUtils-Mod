package net.xabyxd.ServerUtils.commands;

import java.util.List;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class CommandGetLocation extends CommandBase {
    
    @Override
    public String getCommandName() {
        return "getlocation";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/getlocation <player>";
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

    // get player name and uuid
    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if (args.length == 0) {
            sender.addChatMessage(
                new ChatComponentText(EnumChatFormatting.YELLOW + "[INFO] " + EnumChatFormatting.WHITE + "Please specify a player.")
            );
            return;
        } else if (args.length == 1) {
            String PlayerName = args[0];
            EntityPlayer target = MinecraftServer.getServer().getConfigurationManager().func_152612_a(PlayerName);

            if (target == null) {
                sender.addChatMessage(
                    new ChatComponentText(EnumChatFormatting.YELLOW + "[INFO] " + EnumChatFormatting.WHITE + "Player: " + PlayerName + " not found or (offline?).")
                );
                return;
            }

            String PlayerUUID = target.getUniqueID().toString();
            sender.addChatMessage(
                new ChatComponentText(EnumChatFormatting.YELLOW + "[INFO] " + EnumChatFormatting.WHITE + "Player: " + PlayerName + " with UUID: " + PlayerUUID + " is at: " + Math.floor(target.posX) + ", " + Math.floor(target.posY) + ", " + Math.floor(target.posZ) + ".")
            );
        }
    }
}
