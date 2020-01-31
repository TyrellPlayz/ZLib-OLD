package com.tyrellplayz.zlib;

import com.tyrellplayz.zlib.event.ResourcesReloadedEvent;
import com.tyrellplayz.zlib.proxy.ClientProxy;
import com.tyrellplayz.zlib.proxy.CommonProxy;
import com.tyrellplayz.zlib.proxy.ServerProxy;
import com.tyrellplayz.zlib.registry.Registries;
import com.tyrellplayz.zlib.world.WorldGenerator;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.IReloadableResourceManager;
import net.minecraft.resources.IResourceManager;
import net.minecraft.resources.ResourcePackType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerAboutToStartEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(ZLib.MOD_ID)
public class ZLib extends ZMod {
    public static final Logger LOGGER = LogManager.getLogger(ZLib.MOD_NAME);

    public static final String MOD_ID = "zlib";
    public static final String MOD_NAME = "ZLib";

    public static CommonProxy proxy = DistExecutor.runForDist(() -> ClientProxy::new, () -> ServerProxy::new);

    public ZLib() {
        super(MOD_ID);
        FMLJavaModLoadingContext.get().getModEventBus().register(Registries.class);

        MinecraftForge.EVENT_BUS.addListener(this::onServerAboutToStart);
    }

    @Override
    public void onCommonSetup(FMLCommonSetupEvent event) {
        WorldGenerator.initFeatures();
        proxy.onCommonSetup(event);
    }

    @Override
    public void onClientSetup(FMLClientSetupEvent event) {
        Minecraft minecraft = Minecraft.getInstance();
        IResourceManager resourceManager = minecraft.getResourceManager();
        if(resourceManager instanceof IReloadableResourceManager) {
            IReloadableResourceManager reloadableResourceManager = (IReloadableResourceManager) resourceManager;
            reloadableResourceManager.addReloadListener(new ResourcesReloadedEvent.ClientReloadListener());
        }
    }

    public void onServerAboutToStart(FMLServerAboutToStartEvent event) {
        event.getServer().getResourceManager().addReloadListener(new ResourcesReloadedEvent.ServerReloadListener(event.getServer()));
    }

}
