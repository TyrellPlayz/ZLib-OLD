package com.tyrellplayz.zlib.block;

import com.tyrellplayz.zlib.tile.ZTile;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.IFluidState;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

import javax.annotation.Nullable;

/**
 * The base class for block.
 * @author TyrellPlayz
 * @since 0.1.0
 */
public abstract class ZBlock extends Block {

    public ZBlock(Properties properties) {
        super(properties);
    }

    @Override
    public int getComparatorInputOverride(BlockState blockState, World worldIn, BlockPos pos) {
        return Container.calcRedstone(worldIn.getTileEntity(pos));
    }

    @Override
    public boolean hasComparatorInputOverride(BlockState state) {
        return this.hasTileEntity(state);
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        TileEntity tile = worldIn.getTileEntity(pos);
        // If the tile has an Inventory, drop all contained items.
        if(tile instanceof ZTile) {
            ((ZTile) tile).onBlockPlacedBy(worldIn,pos,state,placer,stack);
        }
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
    }

    @Override
    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        // Checks if the block is a completely different block.
        if (state.hasTileEntity() && (state.getBlock() != newState.getBlock() || !newState.hasTileEntity())) {
            TileEntity tile = worldIn.getTileEntity(pos);
            // If the tile has an Inventory, drop all contained items.
            if(tile instanceof IInventory) {
                InventoryHelper.dropInventoryItems(worldIn,pos,(IInventory)tile);
                worldIn.updateComparatorOutputLevel(pos,this);
            }else if(tile instanceof ZTile) {
                ((ZTile) tile).onRemoved(worldIn, pos);
            }
        }
        super.onReplaced(state,worldIn,pos,newState,isMoving);
    }

    @Override
    public boolean eventReceived(BlockState state, World worldIn, BlockPos pos, int id, int param) {
        super.eventReceived(state, worldIn, pos, id, param);
        TileEntity tile = worldIn.getTileEntity(pos);
        return tile != null && tile.receiveClientEvent(id,param);
    }
}
