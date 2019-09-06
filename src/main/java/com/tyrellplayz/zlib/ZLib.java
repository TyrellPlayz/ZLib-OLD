package com.tyrellplayz.zlib;

import com.tyrellplayz.zlib.registry.Registries;
import com.tyrellplayz.zlib.world.WorldGenerator;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("zlib")
public class ZLib extends ZMod {

    public static final String MOD_ID = "zlib";
    public static final String MOD_NAME = "ZLib";

    public ZLib() {
        super();
        FMLJavaModLoadingContext.get().getModEventBus().register(Registries.class);

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::loadComplete);
    }

    @Override
    public void onCommonSetup(FMLCommonSetupEvent event) {

    }

    public void loadComplete(FMLLoadCompleteEvent event) {
        WorldGenerator.initFeatures();
    }

    @Override
    public void onClientSetup(FMLClientSetupEvent event) {

    }
}
