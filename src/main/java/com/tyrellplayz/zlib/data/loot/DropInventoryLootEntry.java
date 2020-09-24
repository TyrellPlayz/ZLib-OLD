package com.tyrellplayz.zlib.data.loot;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.LootPoolEntryType;
import net.minecraft.loot.StandaloneLootEntry;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.loot.functions.ILootFunction;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import java.util.function.Consumer;

public class DropInventoryLootEntry extends StandaloneLootEntry {

    protected DropInventoryLootEntry(int weight, int quality, ILootCondition[] conditions, ILootFunction[] functions) {
        super(weight, quality, conditions, functions);
    }

    @Override
    protected void func_216154_a(Consumer<ItemStack> output, LootContext context) {
        if(context.has(LootParameters.BLOCK_ENTITY)) {
            TileEntity tile = context.get(LootParameters.BLOCK_ENTITY);
            if(tile instanceof IInventory) {
                IInventory inventory = (IInventory)tile;
                for (int i = 0; i < inventory.getSizeInventory(); i++) {
                    // If there is an item stack in the slot drop it.
                    if(!inventory.getStackInSlot(i).isEmpty()) {
                        output.accept(inventory.getStackInSlot(i));
                        inventory.setInventorySlotContents(i,ItemStack.EMPTY);
                    }
                }
            }else if(tile != null) {
                LazyOptional<IItemHandler> itemHandler = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY);
                itemHandler.ifPresent(handler -> {
                    for (int i = 0; i < handler.getSlots(); i++) {
                        if(!handler.getStackInSlot(i).isEmpty()) {
                            output.accept(handler.getStackInSlot(i));
                            handler.extractItem(i,handler.getStackInSlot(i).getCount(),false);
                        }
                    }
                });
            }
        }
    }

    @Override
    public LootPoolEntryType func_230420_a_() {
        return ZLootFunctions.DROP_INVENTORY;
    }

    public static StandaloneLootEntry.Builder<?> builder() {
        return builder(DropInventoryLootEntry::new);
    }

    public static class Serializer extends StandaloneLootEntry.Serializer<DropInventoryLootEntry> {

        @Override
        protected DropInventoryLootEntry deserialize(JsonObject json, JsonDeserializationContext context, int weight, int quality, ILootCondition[] conditions, ILootFunction[] functions) {
            return new DropInventoryLootEntry(weight,quality,conditions,functions);
        }
    }

}
