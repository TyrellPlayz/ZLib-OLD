package com.tyrellplayz.zlib.data.loot;

import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.loot.*;
import net.minecraft.loot.conditions.SurvivesExplosion;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;

import java.util.Arrays;
import java.util.Collection;

/**
 * Credit to blusunrize: https://github.com/BluSunrize/ImmersiveEngineering/blob/1.16.3/src/main/java/blusunrize/immersiveengineering/common/data/BlockLoot.java
 */
public abstract class AbstractBlockLootProvider extends LootProvider {

    public AbstractBlockLootProvider(DataGenerator generator, String modId) {
        super(generator, modId);
    }

    @Override
    public String getName() {
        return "BlockLootTables";
    }

    /**
     * Goes through the list of given blocks and registers them as self dropping if they are not all ready registered.
     * @param registeredBlocks A list of all the mods registered blocks.
     */
    protected void registerAllRemainingAsDefault(Collection<Block> registeredBlocks) {
        registeredBlocks.forEach(block -> {
            if(!TABLES.containsKey(getTableLocation(block.getRegistryName()))) registerSelfDropping(block);
        });
    }

    /**
     * Drops the blocks inventory contents.
     * @return
     */
    protected LootPool.Builder dropInventory() {
        return createPoolBuilder().addEntry(DropInventoryLootEntry.builder());
    }

    protected LootTable.Builder dropProvider(IItemProvider item) {
        return LootTable.builder().addLootPool(singleItem(item));
    }

    protected LootPool.Builder singleItem(IItemProvider item) {
        return createPoolBuilder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(item));
    }

    protected LootPool.Builder createPoolBuilder() {
        return LootPool.builder().acceptCondition(SurvivesExplosion.builder());
    }

    protected void register(Block b, LootPool.Builder... pools) {
        LootTable.Builder builder = LootTable.builder();
        for(LootPool.Builder pool : pools) builder.addLootPool(pool);
        register(b, builder);
    }

    protected void register(Block b, LootTable.Builder table) {
        register(b.getRegistryName(), table);
    }

    protected void register(ResourceLocation name, LootTable.Builder table) {
        if(TABLES.put(getTableLocation(name), table.setParameterSet(LootParameterSets.BLOCK).build())!=null)
            throw new IllegalStateException("Duplicate loot table "+name);
    }

    protected void registerSelfDropping(Block block, LootPool.Builder... pool) {
        LootPool.Builder[] withSelf = Arrays.copyOf(pool,pool.length+1);
        withSelf[withSelf.length-1] = singleItem(block);
        register(block,withSelf);
    }

    protected ResourceLocation getTableLocation(ResourceLocation in) {
        return new ResourceLocation(in.getNamespace(), "blocks/"+in.getPath());
    }

}
