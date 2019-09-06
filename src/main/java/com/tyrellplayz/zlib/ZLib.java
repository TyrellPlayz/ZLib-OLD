package com.tyrellplayz.zlib;

import com.tyrellplayz.zlib.registry.Registries;
import com.tyrellplayz.zlib.world.WorldGenerator;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(ZLib.MOD_ID)
public class ZLib extends ZMod {

    public static final String MOD_ID = "zlib";
    public static final String MOD_NAME = "ZLib";

    public ZLib() {
        super(MOD_ID);
        FMLJavaModLoadingContext.get().getModEventBus().register(Registries.class);
    }

    @Override
    public void onCommonSetup(FMLCommonSetupEvent event) {
        WorldGenerator.initFeatures();
    }

}
