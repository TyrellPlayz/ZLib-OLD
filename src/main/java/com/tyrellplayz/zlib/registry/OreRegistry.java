package com.tyrellplayz.zlib.registry;

import com.tyrellplayz.zlib.world.ore.Ore;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

public abstract class OreRegistry {

    protected static final List<Ore> ORES = new ArrayList<>();

    protected static Ore register(String id, Ore ore){
        return register(new ResourceLocation(id),ore);
    }

    protected static Ore register(ResourceLocation id, Ore ore){
        ore.setRegistryName(id);
        ORES.add(ore);
        return ore;
    }

    @SubscribeEvent
    public static void registerItems(final RegistryEvent.Register<Ore> event) {
        ORES.forEach(ore -> event.getRegistry().register(ore));
    }

}
