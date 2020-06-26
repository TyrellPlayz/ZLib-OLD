package com.tyrellplayz.zlib.data;

import com.google.gson.JsonObject;
import com.tyrellplayz.zlib.block.ICustomBlockModel;
import com.tyrellplayz.zlib.item.ICustomItemModel;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ItemModelProvider extends AbstractDataProvider {

    private final Collection<Block> BLOCKS;
    private final Collection<Item> ITEMS;

    public ItemModelProvider(DataGenerator generator, String modId, Collection<Block> BLOCKS, Collection<Item> ITEMS) {
        super(generator, modId,DataProviderType.ITEM_MODELS);
        this.BLOCKS = BLOCKS;
        this.ITEMS = ITEMS;
    }

    @Override
    public void act(DirectoryCache cache) throws IOException {
        File modelInputFolder = getInputPath().toFile();
        File modelOutputFolder = getOutputPath().toFile();

        // Go though each registered item.
        for (Item item : ITEMS) {
            File modelFileIn = new File(modelInputFolder,item.getRegistryName().getPath()+".json");
            if(modelFileIn.exists()) continue;
            JsonObject modelObject;
            if(item instanceof ICustomItemModel) modelObject = ((ICustomItemModel)item).getItemModelObject(item.getRegistryName());
            else modelObject = createDefaultModel(item);
            IDataProvider.save(GSON,cache,modelObject,new File(modelOutputFolder,item.getRegistryName().getPath()+".json").toPath());
        }

        // Go though each registered block.
        for (Block block : BLOCKS) {
            File modelFileIn = new File(modelInputFolder,block.getRegistryName().getPath()+".json");
            if(modelFileIn.exists()) continue;
            JsonObject modelObject;
            if(block instanceof ICustomItemModel) modelObject = ((ICustomItemModel)block).getItemModelObject(block.getRegistryName());
            else modelObject = createDefaultModel(block);
            IDataProvider.save(GSON,cache,modelObject,new File(modelOutputFolder,block.getRegistryName().getPath()+".json").toPath());
        }

    }

    public JsonObject createDefaultModel(Item item) {
        JsonObject modelObject = new JsonObject();
        modelObject.addProperty("parent","item/generated");

        JsonObject texturesObject = new JsonObject();
        texturesObject.addProperty("layer0",item.getRegistryName().getNamespace()+":item/"+item.getRegistryName().getPath());
        modelObject.add("textures",texturesObject);

        return modelObject;
    }

    public JsonObject createDefaultModel(Block block) {
        JsonObject modelObject = new JsonObject();
        modelObject.addProperty("parent",block.getRegistryName().getNamespace()+":block/"+block.getRegistryName().getPath());
        return modelObject;
    }

    @Override
    public String getName() {
        return "ItemModel";
    }

}
