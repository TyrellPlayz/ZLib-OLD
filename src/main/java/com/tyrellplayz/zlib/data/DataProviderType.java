package com.tyrellplayz.zlib.data;

import net.minecraft.data.TagsProvider;
import net.minecraft.resources.ResourcePackType;

public enum DataProviderType {

    // Client (client)
    BLOCK_STATES("blockstates",ResourcePackType.CLIENT_RESOURCES),
    BLOCK_MODELS("models/block",ResourcePackType.CLIENT_RESOURCES),
    ITEM_MODELS("models/item",ResourcePackType.CLIENT_RESOURCES),

    // Server (data)
    RECIPES("recipes",ResourcePackType.SERVER_DATA),
    ADVANCEMENTS("advancements",ResourcePackType.SERVER_DATA),
    LOOT_TABLES("loot_tables",ResourcePackType.SERVER_DATA);

    private final String directoryName;
    private final ResourcePackType packType;

    DataProviderType(String directoryNameIn, ResourcePackType packType) {
        this.directoryName = directoryNameIn;
        this.packType = packType;
    }

    public String getDirectoryName() {
        return this.directoryName;
    }

    public ResourcePackType getPackType() {
        return packType;
    }
}
