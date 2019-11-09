package com.tyrellplayz.zlib.proxy;

import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public interface CommonProxy {

    void onCommonSetup(FMLCommonSetupEvent event);

    void onClientSetup(FMLClientSetupEvent event);

}
