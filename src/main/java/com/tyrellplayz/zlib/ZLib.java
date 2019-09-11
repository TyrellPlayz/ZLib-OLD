package com.tyrellplayz.zlib;

import com.tyrellplayz.zlib.proxy.ClientProxy;
import com.tyrellplayz.zlib.proxy.CommonProxy;
import com.tyrellplayz.zlib.proxy.ServerProxy;
import com.tyrellplayz.zlib.registry.Registries;
import com.tyrellplayz.zlib.world.WorldGenerator;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
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
    }

    @Override
    public void onCommonSetup(FMLCommonSetupEvent event) {
        WorldGenerator.initFeatures();
        proxy.onCommonSetup(event);
    }

}
