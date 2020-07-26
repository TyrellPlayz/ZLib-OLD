package com.tyrellplayz.zlib.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class BurnableItem extends Item {

    private int burnTime;

    /**
     *
     * @param burnTimeIn - The item burn time in ticks.
     * @param propertiesIn - The {@link Properties} of the item.
     */
    public BurnableItem(int burnTimeIn, Properties propertiesIn) {
        super(propertiesIn);
        this.burnTime = burnTimeIn;
    }

    @Override
    public int getBurnTime(ItemStack itemStack) {
        return burnTime;
    }
}
