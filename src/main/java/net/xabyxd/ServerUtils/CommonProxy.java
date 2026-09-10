package net.xabyxd.ServerUtils;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import net.minecraft.init.Blocks;
import net.xabyxd.ServerUtils.commands.CommandGetLocation;
import net.xabyxd.ServerUtils.commands.CommandGreet;
import net.xabyxd.ServerUtils.config.Config;
import net.xabyxd.ServerUtils.events.PlayerJoinHandler;
import net.xabyxd.ServerUtils.events.VanillaJoinMessageFilter;
import net.xabyxd.ServerUtils.utils.VersionChecker;

public class CommonProxy {

    public void preInit(FMLPreInitializationEvent event) {
        // config init
        Config.synchronizeConfiguration();

        Serverutils.LOGGER.info(Config.configGenerationTest);
        Serverutils.LOGGER.info("This is a configuration test! " + Serverutils.VERSION);

        // logDimensionChanges boolean option
        Serverutils.LOGGER.info("logDimensionChanges: " + Config.logDimensionChanges);
    }

    public void init(FMLInitializationEvent event) {

        // Version checker init
        VersionChecker.startCheck();

        Serverutils.LOGGER.info("==== Server Utils v" + Serverutils.VERSION + " loaded! ====");
        Serverutils.LOGGER.info("By: xabyxd");
        
        // only for testing purposes
        Serverutils.LOGGER.info("DIRT BLOCK >> " + Blocks.dirt.getUnlocalizedName());

        // Event registration
        FMLCommonHandler.instance().bus().register(new PlayerJoinHandler());
        FMLCommonHandler.instance().bus().register(new VanillaJoinMessageFilter());
        Serverutils.LOGGER.info("Events registered!");
        Serverutils.LOGGER.info("=====================================");
    }

    public void postInit(FMLPostInitializationEvent event) {
        // iteration with other mods
    }

    public void serverStarting(FMLServerStartingEvent event) {
        event.registerServerCommand(new CommandGreet());
        event.registerServerCommand(new CommandGetLocation());
        Serverutils.LOGGER.info("Commands registered!");
    }
}