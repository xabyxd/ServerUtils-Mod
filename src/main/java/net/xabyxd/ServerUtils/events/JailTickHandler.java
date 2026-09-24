package net.xabyxd.ServerUtils.events;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.xabyxd.ServerUtils.utils.JailLocation;
import net.xabyxd.ServerUtils.utils.JailManager;
import net.xabyxd.ServerUtils.utils.JailedPlayer;

public class JailTickHandler {

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        if (!(event.player instanceof EntityPlayerMP)) return;

        EntityPlayerMP player = (EntityPlayerMP) event.player;
        String uuid = player.getGameProfile().getId().toString();

        JailedPlayer jailedPlayer = JailManager.get(uuid);
        if (jailedPlayer == null) return;

        if (jailedPlayer.isExpired()) {
            JailManager.unjail(uuid);
            player.addChatMessage(
                new ChatComponentText(EnumChatFormatting.GREEN + "You have been released from jail. Enjoy your freedom!")
            );
            return;
        }

        JailLocation loc = JailManager.getJailLocation();
        if (loc == null) return;

        double dx = player.posX - loc.x;
        double dy = player.posY - loc.y;
        double dz = player.posZ - loc.z;
        double distanceSq = dx * dx + dy * dy + dz * dz;
        double radius = JailManager.JAIL_RADIUS;

        if (distanceSq > radius * radius) {
            player.setPositionAndUpdate(loc.x, loc.y, loc.z);
            player.addChatMessage(
                new ChatComponentText(EnumChatFormatting.RED + "You cannot leave the jail area! ("
                    + jailedPlayer.getRemainingSeconds() + "s remaining)")
            );
        }
    }
}