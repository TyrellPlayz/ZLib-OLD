package com.tyrellplayz.zlib.registry;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IDataProvider;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.ToIntFunction;

/**
 * Contains methods to register Blocks.
 */
public abstract class BlockRegistry {

    protected static final List<Block> BLOCKS = new ArrayList<>();
    protected static final List<Item> ITEMS = new ArrayList<>();

    /**
     * Registers a block
     * @param registryName The registry name of the block.
     * @param block
     * @return
     */
    protected static Block register(String registryName, Block block){
        return register(registryName,block,new Item.Properties());
    }

    /**
     * Registers a block
     * @param registryName The registry name of the block.
     * @param block
     * @param group
     * @return
     */
    protected static Block register(String registryName, Block block, ItemGroup group){
        return register(registryName,block,new Item.Properties().group(group));
    }

    /**
     * Registers a block
     * @param registryName The registry name of the block.
     * @param block
     * @param properties
     * @return
     */
    protected static Block register(String registryName, Block block, Item.Properties properties){
        return register(registryName,block,new BlockItem(block, properties));
    }

    /**
     * Registers a block
     * @param registryName The registry name of the block.
     * @param block
     * @param blockItemFunction
     * @return
     */
    protected static Block register(String registryName, Block block, Function<Block, BlockItem> blockItemFunction) {
        return register(registryName,block,blockItemFunction.apply(block));
    }

    /**
     * Registers a block
     * @param registryName The registry name of the block.
     * @param block
     * @param itemBlock
     * @return
     */
    protected static Block register(String registryName, Block block, BlockItem itemBlock){
        block.setRegistryName(registryName);
        BLOCKS.add(block);
        if(block.getRegistryName() != null){
            itemBlock.setRegistryName(registryName);
            ITEMS.add(itemBlock);
        }
        return block;
    }

    public static ToIntFunction<BlockState> returnLightValueOf(int lightValue) {
        return state -> lightValue;
    }
    
    public static ToIntFunction<BlockState> returnLightValueIfLit(int lightValue) {
        return state -> state.get(BlockStateProperties.LIT) ? lightValue : 0;
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
