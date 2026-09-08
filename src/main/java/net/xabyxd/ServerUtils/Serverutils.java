package net.xabyxd.ServerUtils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;

@Mod(
    modid = Serverutils.MODID,
    version = Serverutils.VERSION,
    name = "Server Utils",
    dependencies = "required-after:Forge@[10.13.4.1614]", // espcecify Forge version (just in case)
    acceptedMinecraftVersions = "[1.7.10]",
    acceptableRemoteVersions = "*" // make the mod only server-side
)

public class Serverutils {
    public static final String MODID = "serverutils";
    public static final String VERSION = "@VERSION@";
    public static final Logger LOGGER = LogManager.getLogger(MODID);

    public static CommonProxy proxy = new CommonProxy();

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }

    @EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        proxy.serverStarting(event);
    }
}