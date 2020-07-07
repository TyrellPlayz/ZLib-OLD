package com.tyrellplayz.zlib.world.ore;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.Dimension;
import net.minecraft.world.DimensionType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraftforge.registries.ForgeRegistryEntry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Holds all the information to generate ore in a world.
 * TODO: Add support for custom biomes.
 * @author TyrellPlayz
 * @since 0.1.0
 */
public class Ore extends ForgeRegistryEntry<Ore> {

    private final BlockState blockState;
    private final Collection<ResourceLocation> dimensions;
    private OreFeatureConfig.FillerBlockType type;

    private final int veinSize;
    private final int veinsPerChunk;
    private final int minY;
    private final int maxY;

    public Ore(BlockState blockState, Ore.Properties properties) {
        this.blockState = blockState;
        this.dimensions = properties.dimensions;
        this.type = properties.type;
        this.veinSize = properties.veinSize;
        this.veinsPerChunk = properties.veinsPerChunk;
        this.minY = properties.minY;
        this.maxY = properties.maxY;
    }

    @Deprecated
    public Ore(Block block, Ore.Properties properties) {
        this(block.getDefaultState(),properties);
    }

    public BlockState getBlockState() {
        return blockState;
    }

    public Collection<ResourceLocation> getDimensions() {
        return dimensions;
    }

    public OreFeatureConfig.FillerBlockType getType() {
        return type;
    }

    public int getVeinSize() {
        return veinSize;
    }

    public int getVeinsPerChunk() {
        return veinsPerChunk;
    }

    public int getMinY() {
        return minY;
    }

    public int getMaxY() {
        return maxY;
    }

    public static class Properties {

        private List<ResourceLocation> dimensions = new ArrayList<>();
        private OreFeatureConfig.FillerBlockType type;
        private int veinSize;
        private int veinsPerChunk;
        private int minY;
        private int maxY;

        public Properties(int veinSize, int veinsPerChunk, int minY, int maxY) {
            this.veinSize = veinSize;
            this.veinsPerChunk = veinsPerChunk;
            this.minY = minY;
            this.maxY = maxY;
            this.type = OreFeatureConfig.FillerBlockType.NATURAL_STONE;
        }

        public Properties type(OreFeatureConfig.FillerBlockType type) {
            this.type = type;
            return this;
        }

        public Properties addDimension(ResourceLocation dimensionName) {
            this.dimensions.add(dimensionName);
            return this;
        }

    }

}
