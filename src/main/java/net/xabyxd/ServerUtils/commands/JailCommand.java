package net.xabyxd.ServerUtils.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.xabyxd.ServerUtils.utils.JailManager;
import net.xabyxd.ServerUtils.utils.JailedPlayer;
import net.xabyxd.ServerUtils.utils.TimeFormat;

public class JailCommand extends CommandBase {

    @Override
    public String getCommandName() {
        return "jail";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/jail <player name|player UUID> <reason> <time>  (wrap the reason in < >, e.g. /jail Steve <stole diamonds> 60)";
    }

    //@Override
    //public String getCommandHelp(ICommandSender sender) {
    //    return "Jails a player on the server jail for a specified amount of time.";
    //} FUTURE FEATURE

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return sender.canCommandSenderUseCommand(4, this.getCommandName());
    }

    private EntityPlayerMP findPlayerByName(String name) {
        for (Object obj : MinecraftServer.getServer().getConfigurationManager().playerEntityList) {
            EntityPlayerMP player = (EntityPlayerMP) obj;
            if (player.getCommandSenderName().equalsIgnoreCase(name)) {
                return player;
            }
        }
        return null;
    }

    private void sendUsage(ICommandSender sender) {
        sender.addChatMessage(
            new ChatComponentText(EnumChatFormatting.RED + "Usage: /jail <player> <reason> <time>. "
                + "The reason must be wrapped in < >, e.g. /jail Steve <stole diamonds> 60")
        );
    }

    @Override // (Test implementation)
    public void processCommand(ICommandSender sender, String[] args) {
        if (args.length < 3) {
            sendUsage(sender);
            return;
        }

        if (JailManager.getJailLocation() == null) {
            sender.addChatMessage(
                new ChatComponentText(EnumChatFormatting.RED + "No jail location has been set. Use /setjail <x> <y> <z> first.")
            );
            return;
        }

        String playerName = args[0];

        if (!args[1].startsWith("<")) {
            sendUsage(sender);
            return;
        }

        // Find the argument that closes the reason (ends with '>'), leaving room for the time arg after it.
        int reasonEndIndex = -1;
        for (int i = 1; i < args.length - 1; i++) {
            if (args[i].endsWith(">")) {
                reasonEndIndex = i;
                break;
            }
        }

        if (reasonEndIndex == -1) {
            sendUsage(sender);
            return;
        }

        StringBuilder reasonBuilder = new StringBuilder();
        for (int i = 1; i <= reasonEndIndex; i++) {
            if (i > 1) reasonBuilder.append(' ');
            reasonBuilder.append(args[i]);
        }

        String rawReason = reasonBuilder.toString();
        String reason = rawReason.substring(1, rawReason.length() - 1).trim();

        if (reason.isEmpty()) {
            sendUsage(sender);
            return;
        }

        if (reasonEndIndex + 1 >= args.length) {
            sendUsage(sender);
            return;
        }

        long seconds;
        try {
            seconds = Long.parseLong(args[reasonEndIndex + 1]);
        } catch (NumberFormatException e) {
            sender.addChatMessage(
                new ChatComponentText(EnumChatFormatting.RED + "The time must be an integer number of seconds.")
            );
            return;
        }

        if (seconds <= 0) {
            sender.addChatMessage(
                new ChatComponentText(EnumChatFormatting.RED + "The time must be greater than 0.")
            );
            return;
        }

        EntityPlayerMP target = findPlayerByName(playerName);

        if (target == null) {
            sender.addChatMessage(
                new ChatComponentText(EnumChatFormatting.RED + "Player '" + playerName + "' is not online. (UUID lookups for offline players aren't supported yet.)")
            );
            return;
        }

        String uuid = target.getGameProfile().getId().toString();

        if (JailManager.isJailed(uuid)) {
            JailedPlayer existing = JailManager.get(uuid);
            sender.addChatMessage(
                new ChatComponentText(EnumChatFormatting.RED + playerName + " is already jailed ("
                    + TimeFormat.formatTicks(existing.getRemainingSeconds() * 20) + " remaining).")
            );
            return;
        }

        JailManager.jail(uuid, playerName, reason, seconds);

        String formattedTime = TimeFormat.formatTicks(seconds * 20);

        // Feedback to whoever ran the command
        sender.addChatMessage(
            new ChatComponentText(EnumChatFormatting.YELLOW + "[INFO] " + EnumChatFormatting.WHITE
                + "Jailing player: " + playerName + " for " + formattedTime
                + " with reason: " + reason + ".")
        );

        // Notification to the jailed player
        target.addChatMessage(
            new ChatComponentText(EnumChatFormatting.RED + "[JAIL] " + EnumChatFormatting.WHITE
                + "You have been jailed for " + formattedTime + ". Reason: " + reason)
        );
    }
}