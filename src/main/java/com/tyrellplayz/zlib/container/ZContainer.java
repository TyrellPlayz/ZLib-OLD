package com.tyrellplayz.zlib.container;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;
import net.minecraft.world.World;

public abstract class ZContainer extends net.minecraft.inventory.container.Container {

    protected final static int TOTAL_PLAYER_SLOTS = 36;

    protected final Properties properties;
    protected final IInventory containerInventory;
    protected IIntArray containerData;
    protected final World world;

    public ZContainer(int id, Properties properties, PlayerInventory playerInventoryIn) {
        this(id,properties,playerInventoryIn,new Inventory(properties.inventorySize));
    }

    public ZContainer(int id, Properties properties, PlayerInventory playerInventory, IInventory containerInventory) {
        this(id,properties,playerInventory,containerInventory,properties.dataSize == 0 ? null : new IntArray(properties.dataSize));
    }

    public ZContainer(int id, Properties properties, PlayerInventory playerInventory, IInventory containerInventory, IIntArray containerData) {
        super(properties.type, id);
        this.properties = properties;
        assertInventorySize(containerInventory, properties.inventorySize);
        if(containerData != null) assertIntArraySize(containerData, properties.dataSize);
        this.containerInventory = containerInventory;
        this.containerData = containerData;
        this.world = playerInventory.player.world;

        createSlots(playerInventory,containerInventory);
        if(containerData != null) this.trackIntArray(containerData);
    }

    /**
     * Is called within the classes constructor to create the containers slots.
     * @param playerInventory The players inventory.
     * @param containerInventory The containers inventory.
     */
    public abstract void createSlots(PlayerInventory playerInventory, IInventory containerInventory);

    /**
     * Creates the slots for the players inventory.
     * To be called in the constructor after the containers inventory slots.
     * @param playerInventory The players inventory.
     */
    protected void createPlayerInventory(int xPos, int yPos, PlayerInventory playerInventory) {
        // Main inventory.
        for(int y = 0; y < 3; ++y) {
            for(int x = 0; x < 9; ++x) {
                this.addSlot(new Slot(playerInventory, x + y * 9 + 9, xPos + x * 18, yPos + y * 18));
            }
        }
        // Hotbar.
        for(int x = 0; x < 9; ++x) {
            this.addSlot(new Slot(playerInventory, x, xPos + x * 18, yPos+58));
        }
        //  9   10  11  12  13  14  15  16  17
        //  18  19  20  21  22  23  24  25  26
        //  27  28  29  30  31  32  33  34  35
        //
        //  0   1   2   3   4   5   6   7   8
    }

    /**
     * @return The first index of the players inventory. The top left corner,
     */
    public int getPlayerInventoryStartIndex() {
        return containerInventory.getSizeInventory();
    }

    public int getPlayerHotBarStartIndex() {
        return getPlayerInventoryStartIndex()+(TOTAL_PLAYER_SLOTS-9);
    }

    public int getPlayerInventoryEndIndex() {
        return getPlayerInventoryStartIndex()+(TOTAL_PLAYER_SLOTS-1);
    }

    public boolean slotInMainPlayerInventory(int slotIndex) {
        return slotIndex >= getPlayerInventoryStartIndex() && slotIndex <= getPlayerHotBarStartIndex()-1;
    }

    public boolean slotInPlayersHotBar(int slotIndex) {
        return slotIndex >= getPlayerHotBarStartIndex() && slotIndex <= getPlayerInventoryEndIndex();
    }

    /**
     * Transfers a itemStack to the containers inventory from the players inventory.
     * @param itemStack The itemStack to be transferred.
     * @param slotIndex The slot index of the item to be transferred.
     * @return
     */
    public abstract TransferResponse transferStackToContainer(ItemStack itemStack, int slotIndex);

    /**
     * Handle when the stack in slot {@code slotIndex} is shift-clicked. Normally this moves the stack between the player
     * inventory and the other inventory(s).
     * @param player The player.
     * @param slotIndex The slot that was shift-clicked.
     */
    @Override
    public ItemStack transferStackInSlot(PlayerEntity player, int slotIndex) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(slotIndex);
        if (slot != null && slot.getHasStack()) {

            // 0 is inputSlot
            // 1 is Fuel slot
            // 2 is output slot

            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            // If the slot is in the containers inventory then move it to the players inventory.
            if(slotIndex < getPlayerInventoryStartIndex()) {
                if (!this.mergeItemStack(itemstack1, getPlayerInventoryStartIndex(), getPlayerInventoryEndIndex()+1, false)) return ItemStack.EMPTY;
                slot.onSlotChange(itemstack1, itemstack);

            // Else the slot is in the players inventory so move it into a valid slot in the containers inventory.
            }else {
                TransferResponse transferResponse = transferStackToContainer(itemstack,slotIndex);
                // If the response is not null then the itemstack can be transferred to a slot.
                if(transferResponse != null) {
                    if (!this.mergeItemStack(itemstack1, transferResponse.startIndex, transferResponse.endIndex, false)) {
                        return ItemStack.EMPTY;
                    }
                }
                // If the itemstack can't be moved into the containers inventory then move it around the players inventory.
                // If the slot is the players main inventory then move it into the players hotbar.
                else if (slotInMainPlayerInventory(slotIndex)) {
                    if (!this.mergeItemStack(itemstack1, getPlayerHotBarStartIndex(), getPlayerInventoryEndIndex()+1, false)) {
                        return ItemStack.EMPTY;
                    }
                    // if the slot is in the players hotbar then move it into the players main inventory.
                } else if (slotInPlayersHotBar(slotIndex)) {
                    if (!this.mergeItemStack(itemstack1, getPlayerInventoryStartIndex(), getPlayerHotBarStartIndex(), false)) {
                        return ItemStack.EMPTY;
                    }
                }
            }



            if (itemstack1.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, itemstack1);
        }

        return itemstack;
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return containerInventory.isUsableByPlayer(playerIn);
    }

    public static class Properties {

        private final ContainerType<?> type;
        private final int inventorySize;
        private final int dataSize;

        public Properties(ContainerType<?> type, int inventorySize) {
            this(type,inventorySize,0);
        }

        public Properties(ContainerType<?> type, int inventorySize, IIntArray data) {
            this(type,inventorySize,data.size());
        }

        public Properties(ContainerType<?> type, int inventorySize, int dataSize) {
            this.type = type;
            this.inventorySize = inventorySize;
            this.dataSize = dataSize;
        }

    }

    protected static class TransferResponse {
        private int startIndex;
        private int endIndex;

        public TransferResponse(int startIndex, int endIndex) {
            this.startIndex = startIndex;
            this.endIndex = endIndex;
        }
    }

}
