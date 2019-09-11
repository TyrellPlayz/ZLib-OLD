package com.tyrellplayz.zlib.registry;

import com.tyrellplayz.zlib.ZLib;
import com.tyrellplayz.zlib.world.ore.Ore;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.RegistryBuilder;

public class Registries {

    @SubscribeEvent
    public static void registerRegistries(RegistryEvent.NewRegistry event) {
        // Ore
        RegistryBuilder<Ore> builderOre = new RegistryBuilder<>();
        builderOre.setType(Ore.class);
        ResourceLocation keyOre = new ResourceLocation(ZLib.MOD_ID, "ore");
        builderOre.setName(keyOre);
        builderOre.setDefaultKey(keyOre);
        builderOre.create();
    }

}
