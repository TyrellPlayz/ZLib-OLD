package com.tyrellplayz.zlib.registry;

import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains methods to register Items.
 */
public abstract class ItemRegistry {

    protected static final List<Item> ITEMS = new ArrayList<>();

    /**
     * Registers a new {@link Item}.
     * @param id The id of the {@link Item}.
     * @param item The {@link Item}.
     * @return The {@link Item}.
     */
    protected static Item register(String id, Item item){
        item.setRegistryName(id);
        ITEMS.add(item);
        return item;
    }

    @SubscribeEvent
    public static void registerItems(final RegistryEvent.Register<Item> event) {
        ITEMS.forEach(item -> event.getRegistry().register(item));
    }

}
