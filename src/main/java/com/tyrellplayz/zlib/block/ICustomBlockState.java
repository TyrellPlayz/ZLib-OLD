package com.tyrellplayz.zlib.block;

import com.google.gson.JsonObject;
import net.minecraft.util.ResourceLocation;


public interface ICustomBlockState {

    JsonObject getBlockStateObject(ResourceLocation location);

}
