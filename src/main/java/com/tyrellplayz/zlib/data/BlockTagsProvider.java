package com.tyrellplayz.zlib.data;

import net.minecraft.data.DataGenerator;

public abstract class BlockTagsProvider extends net.minecraft.data.BlockTagsProvider {

    public BlockTagsProvider(DataGenerator generatorIn) {
        super(generatorIn);
    }

    protected abstract void registerTags();

}
