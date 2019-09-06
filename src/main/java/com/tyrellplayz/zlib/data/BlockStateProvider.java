package com.tyrellplayz.zlib.data;

import com.google.common.collect.Lists;
import com.google.gson.JsonObject;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BlockStateProvider extends AbstractDataProvider {

    private final List<Block> BLOCKS;

    public BlockStateProvider(DataGenerator generator, String modId, List<Block> BLOCKS) {
        super(generator, modId);
        this.BLOCKS = BLOCKS;
    }

    @Override
    public void act(DirectoryCache cache) throws IOException {
        // Get all pre created block states.
        List<File> blockStateFiles = new ArrayList<>();
        if(INPUT_PATH != null) {
            File blockStateFolder = INPUT_PATH.resolve("assets/"+MOD_ID+"/blockstates").toFile();
            if(blockStateFolder.exists()) blockStateFiles = Lists.newArrayList(blockStateFolder.listFiles());
        }

        if(BLOCKS.isEmpty()) {
            LOGGER.info("There are no blocks to create blockstate files for.");
            return;
        }

        // Go thought all blocks in the list and try to create a block state file for it.
        for (Block block : BLOCKS) {
            String fileName = block.getRegistryName().getPath()+".json";

            boolean hasFile = false;
            if(blockStateFiles != null) {
                for (File file : blockStateFiles) if (file.getName().equals(fileName)) hasFile = true;
            }
            if(hasFile)continue;

            if(!block.getDefaultState().getProperties().isEmpty())continue;

            JsonObject blockStateJson = createDefaultState(block);
            File file = new File(OUTPUT_PATH.resolve("assets/"+MOD_ID+"/blockstates").toFile(),block.getRegistryName().getPath()+".json");

            IDataProvider.save(GSON,cache,blockStateJson,file.toPath());
        }
    }

    public JsonObject createDefaultState(Block block) {
        JsonObject modelObject = new JsonObject();
        modelObject.addProperty("model",block.getRegistryName().getNamespace()+":block/"+block.getRegistryName().getPath());

        JsonObject normalObject = new JsonObject();
        normalObject.add("",modelObject);

        JsonObject variantsObject = new JsonObject();
        variantsObject.add("variants",normalObject);

        return variantsObject;
    }

    @Override
    public String getName() {
        return "BlockStateProvider";
    }
}
