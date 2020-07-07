package com.tyrellplayz.zlib.data;

import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;

public abstract class ItemTagsProvider extends net.minecraft.data.ItemTagsProvider {

    public ItemTagsProvider(DataGenerator generatorIn, BlockTagsProvider blockTagsProvider) {
        super(generatorIn,blockTagsProvider);
    }

    protected abstract void registerTags();

}
