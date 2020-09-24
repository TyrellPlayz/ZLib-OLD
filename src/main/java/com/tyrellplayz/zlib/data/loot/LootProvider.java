package com.tyrellplayz.zlib.data.loot;

import com.google.common.collect.Multimap;
import com.tyrellplayz.zlib.ZLib;
import com.tyrellplayz.zlib.data.AbstractDataProvider;
import com.tyrellplayz.zlib.data.DataProviderType;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.loot.LootParameterSets;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTableManager;
import net.minecraft.loot.ValidationTracker;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 * Credit to blusunrize: https://github.com/BluSunrize/ImmersiveEngineering/blob/1.16.3/src/main/java/blusunrize/immersiveengineering/common/data/loot/LootGenerator.java
 */
public abstract class LootProvider extends AbstractDataProvider {

    protected final Map<ResourceLocation, LootTable> TABLES = new HashMap<>();

    public LootProvider(DataGenerator generator, String modId) {
        super(generator, modId, DataProviderType.LOOT_TABLES);
    }

    @Override
    public void act(DirectoryCache cache) throws IOException {
        TABLES.clear();

        registerTables();

        ValidationTracker validator = new ValidationTracker(LootParameterSets.GENERIC,resourceLocation -> null,TABLES::get);
        TABLES.forEach((name, lootTable) -> LootTableManager.validateLootTable(validator,name,lootTable));
        Multimap<String,String> validatorProblems = validator.getProblems();
        if(!validatorProblems.isEmpty()) {
            validatorProblems.forEach((name, table) -> ZLib.LOGGER.warn("Found issue with loot table {}: {}",name,table));
            throw new IllegalStateException("Failed to validate loot tables. See logs for more details");
        }else {
            TABLES.forEach((name, lootTable) -> {
                Path path = getOutputPath().resolve(name.getPath()+".json");
                try {
                    IDataProvider.save(GSON,cache,LootTableManager.toJson(lootTable),path);
                }catch (IOException e) {
                    ZLib.LOGGER.error("Could not save loot table {}",path,e);
                }
            });
        }
    }

    protected abstract void registerTables();

}
