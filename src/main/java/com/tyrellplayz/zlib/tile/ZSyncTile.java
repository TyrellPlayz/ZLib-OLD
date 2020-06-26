package com.tyrellplayz.zlib.tile;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntityType;

import javax.annotation.Nullable;

public class ZSyncTile extends ZTile {

    private final int tileEntityTypeInt;

    public ZSyncTile(TileEntityType<?> tileEntityTypeIn, int tileEntityTypeInt) {
        super(tileEntityTypeIn);
        this.tileEntityTypeInt = tileEntityTypeInt;
    }

    @Override
    public CompoundNBT getUpdateTag() {
        return this.write(new CompoundNBT());
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(this.getPos(),tileEntityTypeInt,this.getUpdateTag());
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        this.setPos(pkt.getPos());
        this.read(pkt.getNbtCompound());
    }

    public void sync() {
        markDirty();
        this.world.notifyBlockUpdate(this.getPos(),this.getBlockState(),this.getBlockState(),3);
    }

}
