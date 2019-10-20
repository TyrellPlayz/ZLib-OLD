package com.tyrellplayz.zlib.item;

import com.google.gson.JsonObject;
import net.minecraft.util.ResourceLocation;

public interface ICustomItemModel {

    JsonObject getItemModelObject(ResourceLocation id);

}
