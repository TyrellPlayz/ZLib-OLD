package com.tyrellplayz.zlib.data;

import com.google.common.collect.Maps;
import com.tyrellplayz.zlib.data.loot.LootTables;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTableManager;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LootTableProvider extends AbstractDataProvider {

    private final List<LootTables<?>> LOOT_TABLES = new ArrayList<>();

    public LootTableProvider(DataGenerator generator, String modId) {
        super(generator, modId,DataProviderType.LOOT_TABLES);
    }

    public <T> LootTableProvider addLootTables(LootTables<T> tables) {
        LOOT_TABLES.add(tables);
        return this;
    }

    @Override
    public void act(DirectoryCache cache) throws IOException {
        Map<ResourceLocation, LootTable> map = Maps.newHashMap();
        LOOT_TABLES.forEach(lootTables -> lootTables.getLootTables().forEach((resourceLocation, builder) -> {
            if(map.put(resourceLocation,builder.setParameterSet(lootTables.getParameterSet()).build()) != null) {
                throw new IllegalStateException("Duplicate loot table " + resourceLocation);
            }
        }));

        map.forEach((resourceLocation, lootTable) -> {
            Path path1 = getPath(resourceLocation);
            try {
                IDataProvider.save(GSON, cache, LootTableManager.toJson(lootTable), path1);
            } catch (IOException ioexception) {
                LOGGER.error("Couldn't save loot table {}", path1, ioexception);
            }

        });
    }

    private Path getPath(ResourceLocation id) {
        return getOutputPath().resolve(id.getPath()+".json");
    }

    @Override
    public String getName() {
        return "Z Loot Table";
    }
}
