package net.xabyxd.ServerUtils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import net.minecraft.init.Blocks;
import net.xabyxd.ServerUtils.commands.CommandGreet;
import net.xabyxd.ServerUtils.events.PlayerJoinHandler;

@Mod(modid = Serverutils.MODID, version = Serverutils.VERSION)
public class Serverutils {
    public static final String MODID = "serverutils";
    public static final String VERSION = "@VERSION@";
    public static final Logger LOGGER = LogManager.getLogger(MODID);

    @EventHandler
    public void init(FMLInitializationEvent event) {
        Serverutils.LOGGER.info("==== Server Utils v" + VERSION + " loaded! ====");
        Serverutils.LOGGER.info("By: xabyxd");
        // only for testing purposes
        Serverutils.LOGGER.info("DIRT BLOCK >> " + Blocks.dirt.getUnlocalizedName());

        // Event registration
        FMLCommonHandler.instance().bus().register(new PlayerJoinHandler());
        Serverutils.LOGGER.info("=====================================");
    }

    // Commands registration
    @EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        event.registerServerCommand(new CommandGreet());
    }
}