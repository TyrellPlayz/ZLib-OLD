package com.tyrellplayz.zlib.registry;

import com.tyrellplayz.zlib.world.ore.OreType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

public abstract class OreRegistry {

    protected static final List<OreType> ORES = new ArrayList<>();

    protected static OreType register(OreType ore){
        return register(ore.getBlockState().getBlock().getRegistryName(),ore);
    }

    protected static OreType register(String id, OreType ore){
        return register(new ResourceLocation(id),ore);
    }

    protected static OreType register(ResourceLocation id, OreType ore){
        ore.setRegistryName(id);
        ORES.add(ore);
        return ore;
    }

    @SubscribeEvent
    public static void registerItems(final RegistryEvent.Register<OreType> event) {
        ORES.forEach(ore -> event.getRegistry().register(ore));
    }

}
