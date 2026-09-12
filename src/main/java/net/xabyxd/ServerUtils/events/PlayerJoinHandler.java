package net.xabyxd.ServerUtils.events;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerChangedDimensionEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.DimensionManager;
import net.xabyxd.ServerUtils.Serverutils;
import net.xabyxd.ServerUtils.config.Config;

public class PlayerJoinHandler {

    @SubscribeEvent
    public void onDimensionChange(PlayerChangedDimensionEvent event) { // FIX: now the event prints the old and new dimension names
        if (!Config.logDimensionChanges) return;
        String fromDimName = DimensionManager.createProviderFor(event.fromDim).getDimensionName();
        String toDimName = DimensionManager.createProviderFor(event.toDim).getDimensionName();
        Serverutils.LOGGER.info("[INFO] " + event.player.getCommandSenderName() + " changed from dim " + event.fromDim + " ( " + fromDimName + " ) to dim " + event.toDim + " ( " + toDimName + " ).");
    }

    @SubscribeEvent
    public void onPlayerJoin(PlayerLoggedInEvent event) {
        EntityPlayer player = (EntityPlayer) event.player;
        String username = player.getDisplayName();

        broadcastJoin(username);
        sendWelcome(player, username);

        Serverutils.LOGGER.info("[INFO] " + username + " joined the world!");
    }

    @SubscribeEvent
    public void onPlayerLeave(PlayerLoggedOutEvent event) {
        EntityPlayer player = (EntityPlayer) event.player;
        String username = player.getDisplayName();

        broadcastLeave(username);

        Serverutils.LOGGER.info("[INFO] " + username + " left the world!");
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

    private void broadcastLeave(String username) {
        MinecraftServer.getServer().getConfigurationManager().sendChatMsg(
            new ChatComponentText(
                EnumChatFormatting.RED + "« " + EnumChatFormatting.BOLD + username +
                EnumChatFormatting.RESET + EnumChatFormatting.RED + " has left the server!"
            )
        );
    }

    private void sendWelcome(EntityPlayer player, String username) {
        String separator = EnumChatFormatting.DARK_GRAY + "―――――――――――――――――――――";

        player.addChatMessage(new ChatComponentText(separator));

        if (isOp(player)) {
            player.addChatMessage(new ChatComponentText(
                EnumChatFormatting.RED + "" + EnumChatFormatting.BOLD + Config.opUserWelcomeMessage + username + "!"
            ));
            player.addChatMessage(new ChatComponentText(
                EnumChatFormatting.GRAY + "You are logged in as " + EnumChatFormatting.RED + "OP" +
                EnumChatFormatting.GRAY + ". Type " + EnumChatFormatting.AQUA + "/help" +
                EnumChatFormatting.GRAY + " to see admin commands."
            ));
        } else {
            player.addChatMessage(new ChatComponentText(
                EnumChatFormatting.GOLD + "" + EnumChatFormatting.BOLD + Config.normalUserWelcomeMessage + username + "!"
            ));
            player.addChatMessage(new ChatComponentText(
                EnumChatFormatting.GRAY + "Use " + EnumChatFormatting.AQUA + "/info" +
                EnumChatFormatting.GRAY + " to see the available server info."
            ));
        }

        player.addChatMessage(new ChatComponentText(separator));
    }
}