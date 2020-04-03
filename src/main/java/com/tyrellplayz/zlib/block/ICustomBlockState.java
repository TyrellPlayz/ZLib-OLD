package com.tyrellplayz.zlib.block;

import com.google.gson.JsonObject;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;


@OnlyIn(Dist.CLIENT)
public interface ICustomBlockState {

    JsonObject getBlockStateObject(ResourceLocation registryName);

    static JsonObject getDefaultFacingState(ResourceLocation registryName) {
        JsonObject object = new JsonObject();
        JsonObject variants = new JsonObject();

        JsonObject facingNorth = new JsonObject();
        facingNorth.addProperty("model",registryName.getNamespace()+":block/"+registryName.getPath());
        JsonObject facingEast = new JsonObject();
        facingEast.addProperty("model",registryName.getNamespace()+":block/"+registryName.getPath());
        facingEast.addProperty("y",90);
        JsonObject facingSouth = new JsonObject();
        facingSouth.addProperty("model",registryName.getNamespace()+":block/"+registryName.getPath());
        facingSouth.addProperty("y",180);
        JsonObject facingWest = new JsonObject();
        facingWest.addProperty("model",registryName.getNamespace()+":block/"+registryName.getPath());
        facingWest.addProperty("y",270);

        variants.add("facing=north",facingNorth);
        variants.add("facing=east",facingEast);
        variants.add("facing=south",facingSouth);
        variants.add("facing=west",facingWest);

        object.add("variants",variants);
        return object;
    }

}
