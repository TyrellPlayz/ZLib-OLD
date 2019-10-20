package com.tyrellplayz.zlib.data;

import com.google.gson.JsonObject;
import com.tyrellplayz.zlib.block.ICustomBlockModel;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

public class BasicBlockModelProvider extends AbstractDataProvider {

    protected final Collection<Block> BLOCKS;

    public BasicBlockModelProvider(DataGenerator generator, String modId, Collection<Block> BLOCKS) {
        super(generator, modId);
        this.BLOCKS = BLOCKS;
    }

    @Override
    public void act(DirectoryCache cache) throws IOException {
        File modelInputFolder = getInputBlockModelFolder(MOD_ID);
        File modelOutputFolder = getOutputBlockModelFolder(MOD_ID);

        for (Block block : BLOCKS) {
            File modelFileIn = new File(modelInputFolder,block.getRegistryName().getPath()+".json");
            if(modelFileIn.exists()) continue;
            JsonObject modelObject;
            if(block instanceof ICustomBlockModel) modelObject = ((ICustomBlockModel)block).getBlockModelObject(block.getRegistryName());
            else modelObject = createDefaultModel(block);
            IDataProvider.save(GSON,cache,modelObject,new File(modelOutputFolder,block.getRegistryName().getPath()+".json").toPath());
        }
    }

    protected JsonObject createDefaultModel(Block block) {
        JsonObject object = new JsonObject();
        object.addProperty("parent","block/cube_all");

        JsonObject texturesObject = new JsonObject();
        texturesObject.addProperty("all",block.getRegistryName().getNamespace()+":block/"+block.getRegistryName().getPath());
        object.add("textures",texturesObject);

        return object;
    }

    @Override
    public String getName() {
        return "BlockModel";
    }
}
