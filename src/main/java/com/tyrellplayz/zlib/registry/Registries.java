package com.tyrellplayz.zlib.registry;

import com.tyrellplayz.zlib.ZLib;
import com.tyrellplayz.zlib.world.ore.Ore;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class Registries {

    @SubscribeEvent
    public static void registerRegistries(RegistryEvent.NewRegistry event) {
        // Ore
        net.minecraftforge.registries.RegistryBuilder<Ore> builder = new net.minecraftforge.registries.RegistryBuilder<>();
        builder.setType(Ore.class);
        ResourceLocation key = new ResourceLocation(ZLib.MOD_ID, "ore");
        builder.setName(key);
        builder.setDefaultKey(key);
        builder.create();

    }

}
