package com.tyrellplayz.zlib.data;

import com.google.gson.JsonObject;
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
import java.util.List;

public class ItemModelProvider extends AbstractDataProvider {

    private List<ItemModelFile> itemModelFiles = new ArrayList<>();
    private File itemModelFolder;

    private final List<Block> BLOCKS;
    private final List<Item> ITEMS;

    public ItemModelProvider(DataGenerator generator, String modId, List<Block> BLOCKS, List<Item> ITEMS) {
        super(generator, modId);
        this.BLOCKS = BLOCKS;
        this.ITEMS = ITEMS;
    }

    @Override
    public void act(DirectoryCache cache) throws IOException {
        this.itemModelFolder = OUTPUT_PATH.resolve("assets/"+MOD_ID+"/models/item").toFile();
        final File itemModelFolderIn = INPUT_PATH.resolve("assets/"+MOD_ID+"/models/item").toFile();

        this.registerItemModels();

        for (ItemModelFile itemModelFile : itemModelFiles) {
            File modelFileIn = new File(itemModelFolderIn,itemModelFile.item.getRegistryName().getPath()+".json");
            if(modelFileIn.exists()) continue;

            File modelFile = new File(itemModelFolder,itemModelFile.item.getRegistryName().getPath()+".json");
            IDataProvider.save(GSON,cache,itemModelFile.toJson(),modelFile.toPath());
        }

        for (Item item : ITEMS) {
            File modelFileIn = new File(itemModelFolderIn,item.getRegistryName().getPath()+".json");
            if(modelFileIn.exists()) continue;

            File modelFile = new File(itemModelFolder,item.getRegistryName().getPath()+".json");
            ItemModelFile itemModelFile = new ItemModelFile(modelFile.toPath(), item);
            IDataProvider.save(GSON,cache,itemModelFile.toJson(),modelFile.toPath());
        }

        for (Block block : BLOCKS) {
            File modelFileIn = new File(itemModelFolderIn,block.getRegistryName().getPath()+".json");
            if(modelFileIn.exists()) continue;

            File modelFile = new File(itemModelFolder,block.getRegistryName().getPath()+".json");
            ItemModelFile itemModelFile = new ItemModelFile(modelFile.toPath(), block);
            IDataProvider.save(GSON,cache,itemModelFile.toJson(),modelFile.toPath());
        }

    }

    private void registerItemModels() {

    }

    private void registerItemModel(Item item, ResourceLocation textureLocation) {
        itemModelFiles.add(new ItemModelFile(itemModelFolder.toPath(),item,textureLocation));
    }

    @Override
    public String getName() {
        return "ItemModelProvider";
    }

    public class ItemModelFile {
        private Path path;
        private ResourceLocation textureLocation;
        private Item item;
        private boolean block;

        public ItemModelFile(Path path, Item item) {
            this(path,item,new ResourceLocation(item.getRegistryName().getNamespace(),"item/"+item.getRegistryName().getPath()));
        }

        public ItemModelFile(Path path, Block block) {
            this(path,block.asItem(),new ResourceLocation(block.getRegistryName().getNamespace(),"block/"+block.getRegistryName().getPath()));
            this.block = true;
        }

        public ItemModelFile(Path path, Item item, ResourceLocation textureLocation) {
            this.path = path;
            this.textureLocation = textureLocation;
            this.item = item;
        }

        public JsonObject toJson() {
            JsonObject object = new JsonObject();

            if(!block) {
                object.addProperty("parent","item/generated");

                JsonObject texturesObject = new JsonObject();
                texturesObject.addProperty("layer0",textureLocation.toString());
                object.add("textures",texturesObject);
            }else {
                object.addProperty("parent",textureLocation.getNamespace()+":"+textureLocation.getPath());
            }

            return object;
        }

    }

}
