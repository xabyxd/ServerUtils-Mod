package net.xabyxd.ServerUtils.events;

import java.util.HashSet;
import java.util.Set;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraftforge.event.world.BlockEvent;
import net.xabyxd.ServerUtils.config.Config;
import net.xabyxd.ServerUtils.utils.LogHelper;

public class BlockBreackEvent {

    private static final Set<Block> WATCHED_BLOCKS = new HashSet<Block>();

    public static void loadWatchedBlocks() {
        WATCHED_BLOCKS.clear();

        for (String entry : Config.watchedBlocksList) {
            String[] parts = entry.split(":", 2);
            if (parts.length != 2) {
                LogHelper.info("Invalid block entry (expected => modid:blockname): " + entry);
                continue;
            }

            Block block = GameRegistry.findBlock(parts[0].trim(), parts[1].trim());

            if (block == null) {
                LogHelper.info("The block for: " + entry + "  was not found. (Mod not installed or incorrect name?)");
                continue;
            }

            WATCHED_BLOCKS.add(block);
        }

        LogHelper.info("Watched blocks loaded: " + WATCHED_BLOCKS.size());
    }

    @SubscribeEvent
    public void onBlockBreak(BlockEvent.BreakEvent event) {
        if (WATCHED_BLOCKS.contains(event.block)) {
            LogHelper.info(event.getPlayer().getCommandSenderName()
            + " broke " + event.block.getUnlocalizedName()
            + " in " + event.x + ", " + event.y + ", " + event.z);
        }
    }
}