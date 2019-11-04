package com.tyrellplayz.zlib.block;

import com.google.gson.JsonObject;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;


@OnlyIn(Dist.CLIENT)
public interface ICustomBlockState {

    JsonObject getBlockStateObject(ResourceLocation location);

}
