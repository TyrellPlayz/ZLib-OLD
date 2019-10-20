package com.tyrellplayz.zlib.data;

import com.google.common.collect.Lists;
import com.google.gson.JsonObject;
import com.tyrellplayz.zlib.block.ICustomBlockState;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.state.properties.BlockStateProperties;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Creates {@link net.minecraft.block.BlockState} files for registered blocks.
 * Only works for blocks with no properties or with they implement {@link ICustomBlockState}.
 */
public class BasicBlockStateProvider extends AbstractDataProvider {

    private final Collection<Block> BLOCKS;

    public BasicBlockStateProvider(DataGenerator generator, String modId, Collection<Block> BLOCKS) {
        super(generator, modId);
        this.BLOCKS = BLOCKS;
    }

    @Override
    public void act(DirectoryCache cache) throws IOException {
        // If there are no registered blocks, do nothing.
        if(BLOCKS.isEmpty()) {
            LOGGER.info("There are no blocks to create state files for.");
            return;
        }

        // Go though all blocks in the list and try to create a state file for it.
        for (Block block : BLOCKS) {
            String fileName = block.getRegistryName().getPath()+".json";
            File file = new File(getInputBlockStateFolder(MOD_ID),fileName);
            if(file.exists())continue;

            JsonObject blockStateJson;
            if(block instanceof ICustomBlockState) blockStateJson = ((ICustomBlockState)block).getBlockStateObject(block.getRegistryName());
            else {
                // Check if the block can have a state created for it.
                if(!block.getDefaultState().getProperties().isEmpty() && !block.getDefaultState().has(BlockStateProperties.WATERLOGGED)) {
                    LOGGER.warn("Cannot create blockstate file for "+block.getRegistryName());
                    continue;
                }
                blockStateJson = createDefaultState(block);
            }
            // Save the state file.
            IDataProvider.save(GSON,cache,blockStateJson,new File(getOutputBlockStateFolder(MOD_ID),fileName).toPath());
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
        return "BlockState";
    }
}