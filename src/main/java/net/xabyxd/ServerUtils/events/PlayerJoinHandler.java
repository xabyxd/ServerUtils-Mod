package net.xabyxd.ServerUtils.events;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerChangedDimensionEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.xabyxd.ServerUtils.Serverutils;

public class PlayerJoinHandler {

    @SubscribeEvent
    // TODO: add a bool option config to disable or enable this event
    public void onDimensionChange(PlayerChangedDimensionEvent event) {
        Serverutils.LOGGER.info("[INFO] " + event.player.getCommandSenderName() + " changed from dim " + event.fromDim + " to dim " + event.toDim);
    }

    @SubscribeEvent
    public void onPlayerJoin(PlayerLoggedInEvent event) {
        EntityPlayer player = (EntityPlayer) event.player;
        String username = player.getDisplayName();

        broadcastJoin(username);
        sendWelcome(player, username);

        Serverutils.LOGGER.info("[INFO] " + username + " joined the world!");
    }

    private boolean isOp(EntityPlayer player) {
        return MinecraftServer.getServer().getConfigurationManager().func_152596_g(player.getGameProfile());
    }

    private void broadcastJoin(String username) {
        MinecraftServer.getServer().getConfigurationManager().sendChatMsg(
            new ChatComponentText(
                EnumChatFormatting.GREEN + "» " + EnumChatFormatting.BOLD + username +
                EnumChatFormatting.RESET + EnumChatFormatting.GREEN + " has joined the server!"
            )
        );
    }

    private void sendWelcome(EntityPlayer player, String username) {
        String separator = EnumChatFormatting.DARK_GRAY + "―――――――――――――――――――――";

        player.addChatMessage(new ChatComponentText(separator));

        if (isOp(player)) {
            player.addChatMessage(new ChatComponentText(
                EnumChatFormatting.RED + "" + EnumChatFormatting.BOLD + "Welcome back, " + username + "!"
            ));
            player.addChatMessage(new ChatComponentText(
                EnumChatFormatting.GRAY + "You are logged in as " + EnumChatFormatting.RED + "OP" +
                EnumChatFormatting.GRAY + ". Type " + EnumChatFormatting.AQUA + "/help" +
                EnumChatFormatting.GRAY + " to see admin commands."
            ));
        } else {
            player.addChatMessage(new ChatComponentText(
                EnumChatFormatting.GOLD + "" + EnumChatFormatting.BOLD + "Welcome, " + username + "!"
            ));
            player.addChatMessage(new ChatComponentText(
                EnumChatFormatting.GRAY + "Use " + EnumChatFormatting.AQUA + "/kits" +
                EnumChatFormatting.GRAY + " to see the available kits."
            ));
        }

        player.addChatMessage(new ChatComponentText(separator));
    }
}