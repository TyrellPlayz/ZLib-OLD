package com.tyrellplayz.zlib.util.helpers;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;

import javax.annotation.Nullable;

public class MathsHelper {

    private MathsHelper(){}

    public static int round(double d) {
        return (int) (d + 0.5D);
    }

    public static int min(int value, int min){
        return MathHelper.clamp(value,min,Integer.MAX_VALUE);
    }

    public static double min(double value, double min){
        return MathHelper.clamp(value,min,Double.MAX_VALUE);
    }

    public static int max(int value, int max){
        return MathHelper.clamp(value,0,max);
    }

    public static double max(double value, double max){
        return MathHelper.clamp(value,0,max);
    }

    public static double getDistance(BlockPos pos1, BlockPos pos2) {
        return Math.sqrt(pos1.distanceSq(pos2.getX()+0.5,pos2.getY()+0.5,pos2.getZ()+0.5,true));
    }

    @Nullable
    public static BlockPos getBlockPosPlayerIsLookingAt(PlayerEntity player){
        RayTraceResult rayTraceBlock = player.pick(20.0D, 0.0F, false);
        if(rayTraceBlock.getType() == RayTraceResult.Type.BLOCK) {
            return ((BlockRayTraceResult)rayTraceBlock).getPos();
        }
        return null;
    }

}
