package com.tyrellplayz.zlib.data.loot;

import com.google.common.collect.Maps;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootParameterSet;
import net.minecraft.world.storage.loot.LootTable;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;

public abstract class LootTables<T> {

    private final LootParameterSet parameterSet;
    public final Map<ResourceLocation, LootTable.Builder> lootTables = Maps.newHashMap();
    public final Collection<T> types;

    public LootTables(LootParameterSet parameterSet, Collection<T> types) {
        this.parameterSet = parameterSet;
        this.types = types;
        createTables();
        types.forEach(type -> {
            // If the lootTables map doesn't contain the types loot-table then a loot-table was not registered for it.
            if(!lootTables.containsKey(getLootTable(type)) && getLootTable(type) != net.minecraft.world.storage.loot.LootTables.EMPTY) {
                // So give it the default loot-map.
                LootTable.Builder builder = getDefaultDrop(type);
                // If there is no default drop then do nothing.
                if(builder != null) registerLootTable(type,builder);
            }
        });
    }

    /**
     * Create the loot tables here.
     */
    protected abstract void createTables();

    /**
     * Gets called if a type has not been given a loot table. Can be null
     * @param type
     * @return
     */
    protected abstract LootTable.Builder getDefaultDrop(T type);

    protected abstract ResourceLocation getLootTable(T type);

    protected void registerLootTable(T type, LootTable.Builder builder) {
        this.registerLootTable(getLootTable(type), builder);
    }

    protected void registerLootTable(T type, Function<T, LootTable.Builder> factory) {
        this.registerLootTable(getLootTable(type), factory.apply(type));
    }

    protected void registerLootTable(ResourceLocation resourceLocation, LootTable.Builder table) {
        if(resourceLocation != net.minecraft.world.storage.loot.LootTables.EMPTY) {
            this.lootTables.put(resourceLocation, table);
        }
    }

    public Map<ResourceLocation, LootTable.Builder> getLootTables() {
        return lootTables;
    }

    public LootParameterSet getParameterSet() {
        return parameterSet;
    }
}
