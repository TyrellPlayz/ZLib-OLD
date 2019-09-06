package com.tyrellplayz.zlib.registry;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains methods to register Blocks.
 */
public abstract class BlockRegistry {

    protected static final List<Block> BLOCKS = new ArrayList<>();
    protected static final List<Item> ITEMS = new ArrayList<>();

    protected static Block register(String id, Block block){
        return register(id,block,new Item.Properties());
    }

    protected static Block register(String id, Block block, ItemGroup group){
        return register(id,block,new Item.Properties().group(group));
    }

    protected static Block register(String id, Block block, Item.Properties properties){
        return register(id,block,new BlockItem(block, properties));
    }

    protected static Block register(String id, Block block, BlockItem itemBlock){
        block.setRegistryName(id);
        BLOCKS.add(block);
        if(block.getRegistryName() != null){
            itemBlock.setRegistryName(id);
            ITEMS.add(itemBlock);
        }
        return block;
    }

    @SubscribeEvent
    public static void registerBlocks(final RegistryEvent.Register<Block> event) {
        BLOCKS.forEach(block -> event.getRegistry().register(block));
    }

    @SubscribeEvent
    public static void registerItems(final RegistryEvent.Register<Item> event) {
        ITEMS.forEach(item -> event.getRegistry().register(item));
    }

}
