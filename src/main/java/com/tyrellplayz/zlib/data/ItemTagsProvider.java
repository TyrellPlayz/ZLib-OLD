package com.tyrellplayz.zlib.data;

import net.minecraft.data.DataGenerator;

public abstract class ItemTagsProvider extends net.minecraft.data.ItemTagsProvider {

    public ItemTagsProvider(DataGenerator generatorIn) {
        super(generatorIn);
    }

    protected abstract void registerTags();

}
