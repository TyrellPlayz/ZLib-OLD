package com.tyrellplayz.zlib.tile;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.INameable;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nullable;

public class ZTile extends TileEntity implements INameable {

    private ITextComponent customName;

    public ZTile(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    @Override
    public ITextComponent getName() {
        return this.customName != null ? this.customName : new TranslationTextComponent("container."+super.getType().getRegistryName().getPath());
    }

    @Override
    public boolean hasCustomName() {
        return this.customName != null;
    }

    @Nullable
    @Override
    public ITextComponent getCustomName() {
        return this.customName;
    }

    public void setCustomName(ITextComponent customName) {
        this.customName = customName;
    }

    @Override
    public void read(CompoundNBT compound) {
        super.read(compound);
        if (compound.contains("CustomName", 8)) this.customName = ITextComponent.Serializer.fromJson(compound.getString("CustomName"));
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        if (this.customName != null) compound.putString("CustomName", ITextComponent.Serializer.toJson(this.customName));
        return compound;
    }
}
