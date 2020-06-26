package com.tyrellplayz.zlib.data.loot;

import net.minecraft.block.Block;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootParameterSets;
import net.minecraft.world.storage.loot.LootTable;

import java.util.Collection;

public abstract class AbstractBlockLootTables extends LootTables<Block> {

    public AbstractBlockLootTables(Collection<Block> blocks) {
        super(LootParameterSets.BLOCK,blocks);
    }

    @Override
    protected LootTable.Builder getDefaultDrop(Block type) {
        return net.minecraft.data.loot.BlockLootTables.dropping(type);
    }

    @Override
    protected ResourceLocation getLootTable(Block type) {
        return type.getLootTable();
    }

    /**
     * Creates a loot table where the block drops itself.
     * @param block
     * @return
     */
    protected void dropsSelf(Block block) {
        drops(block,block);
    }

    /**
     * Creates a loot table where the blocks drops one item.
     * @param drop
     * @return
     */
    protected void drops(Block block, IItemProvider drop) {
        this.registerLootTable(block, net.minecraft.data.loot.BlockLootTables.dropping(drop));
    }

}
