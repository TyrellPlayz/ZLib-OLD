package com.tyrellplayz.zlib.block;

import com.google.gson.JsonObject;
import net.minecraft.util.ResourceLocation;

/**
 * Tells the {@link com.tyrellplayz.zlib.data.BasicBlockModelProvider} that this block has a custom Block model object.
 */
public interface ICustomBlockModel {

    /**
     * Gets the custom block model object.
     * @param id The id of the block.
     * @return The block model object.
     */
    JsonObject getBlockModelObject(ResourceLocation id);

}
