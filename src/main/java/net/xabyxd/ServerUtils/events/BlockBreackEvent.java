package net.xabyxd.ServerUtils.events;

import java.util.HashSet;
import java.util.Set;

import cpw.mods.fml.common.Loader;
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

            String modid = parts[0].trim();
            String blockName = parts[1].trim();

            Block block = GameRegistry.findBlock(modid, blockName);

            if (block == null) {
                if (!Loader.isModLoaded(modid)) {
                    LogHelper.info("Skipping '" + entry + "': mod '" + modid + "' is not installed.");
                } else {
                    LogHelper.info("Skipping '" + entry + "': mod '" + modid + "' is installed but block '" + blockName + "' was not found.");
                }
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