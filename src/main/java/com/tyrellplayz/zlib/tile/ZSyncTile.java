package com.tyrellplayz.zlib.tile;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntityType;

import javax.annotation.Nullable;

public class ZSyncTile extends ZTile {

    public ZSyncTile(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    @Override
    public CompoundNBT getUpdateTag() {
        return this.write(new CompoundNBT());
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(this.getPos(),0,this.write(new CompoundNBT()));
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        this.setPos(pkt.getPos());
        this.read(pkt.getNbtCompound());
    }

    protected void sync() {
        markDirty();
        this.world.notifyBlockUpdate(this.pos,this.getBlockState(),this.getBlockState(),3);
    }

}
