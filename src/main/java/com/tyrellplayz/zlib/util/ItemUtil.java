package com.tyrellplayz.zlib.util;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

public class ItemUtil {

    private ItemUtil() {}

    /**
     * Gets the NBT tag of an {@link ItemStack}. If the {@link ItemStack} has no NBT tag a new one is created for it.
     * @param itemStack The {@link ItemStack} to get the NBT tag from.
     * @return The {@link CompoundNBT} of the {@link ItemStack}.
     */
    public static CompoundNBT getItemTag(ItemStack itemStack) {
        if(!itemStack.hasTag()) itemStack.setTag(new CompoundNBT());
        return itemStack.getTag();
    }

}
