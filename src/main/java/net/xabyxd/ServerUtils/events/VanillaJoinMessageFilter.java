package net.xabyxd.ServerUtils.events;

import java.lang.reflect.Field;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;
import net.xabyxd.ServerUtils.Serverutils;

public class VanillaJoinMessageFilter {

    @SubscribeEvent
    public void onPlayerJoin(PlayerLoggedInEvent event) {
        EntityPlayerMP player = (EntityPlayerMP) event.player;

        player.playerNetServerHandler.netManager.channel().pipeline().addBefore(
            "packet_handler", "serverutils_chat_filter", new ChannelDuplexHandler() {
                @Override
                public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                    try {
                        if (msg instanceof S02PacketChat) {
                            IChatComponent component = extractChatComponent((S02PacketChat) msg);
                            if (component instanceof ChatComponentTranslation) {
                                String key = ((ChatComponentTranslation) component).getKey();
                                if ("multiplayer.player.joined".equals(key)) {
                                    return; // It's ruled out, no call is made to super.write()
                                }
                            }
                        }
                    } catch (Exception e) {
                        // If something fails during packet inspection, we let it pass instead of dropping the connection.
                        Serverutils.LOGGER.warn("Failed to inspect outgoing chat packet", e);
                    }
                    super.write(ctx, msg, promise);
                }
            }
        );
    }

    private static IChatComponent extractChatComponent(S02PacketChat packet) throws IllegalAccessException {
        for (Field field : S02PacketChat.class.getDeclaredFields()) {
            if (IChatComponent.class.isAssignableFrom(field.getType())) {
                field.setAccessible(true);
                return (IChatComponent) field.get(packet);
            }
        }
        return null;
    }
    // TODO: add a goodbye message filter too XD
}