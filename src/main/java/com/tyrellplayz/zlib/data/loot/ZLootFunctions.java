package com.tyrellplayz.zlib.data.loot;

import com.tyrellplayz.zlib.ZLib;
import net.minecraft.loot.ILootSerializer;
import net.minecraft.loot.LootEntry;
import net.minecraft.loot.LootPoolEntryType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public class ZLootFunctions {

    public static final LootPoolEntryType DROP_INVENTORY = registerEntry(new ResourceLocation(ZLib.MOD_ID,"drop_inventory"),new DropInventoryLootEntry.Serializer());

    private static LootPoolEntryType registerEntry(ResourceLocation name, ILootSerializer<? extends LootEntry> lootSerializer){
        return Registry.register(Registry.field_239693_aY_, name, new LootPoolEntryType(lootSerializer));
    }

}
