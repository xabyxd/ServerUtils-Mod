package net.xabyxd.ServerUtils.events;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.xabyxd.ServerUtils.Serverutils;

public class PlayerJoinHandler {

    @SubscribeEvent
    public void onPlayerJoin(PlayerLoggedInEvent event) {
        String username = event.player.getDisplayName();

        MinecraftServer.getServer().getConfigurationManager().sendChatMsg(
            new ChatComponentText(
                EnumChatFormatting.GREEN + "[SERVER] " + username + EnumChatFormatting.WHITE + " has joined the server!!"
            )
        );

        EntityPlayer player = (EntityPlayer) event.player;
        player.addChatMessage(
            new ChatComponentText(
                EnumChatFormatting.GREEN + "[SERVER] " + EnumChatFormatting.WHITE + "Hello " + username + ", use /kits to see the kits."
            )// example text xd
        );

        Serverutils.LOGGER.info(username + " joined the world!");
    }
}