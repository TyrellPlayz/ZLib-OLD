package com.tyrellplayz.zlib.world.ore;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.registries.ForgeRegistryEntry;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Holds all the information to generate ore in a world.
 * @author TyrellPlayz
 * @since 0.1.0
 */
public class Ore extends ForgeRegistryEntry<Ore> {

    private final BlockState blockState;
    private final DimensionType spawnDimension;

    private final int veinSize;
    private final int veinsPerChunk;
    private final int minY;
    private final int maxY;

    public Ore(BlockState blockState, Ore.Properties properties) {
        this.blockState = blockState;
        this.spawnDimension = properties.spawnDimension;
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

    public DimensionType getSpawnDimension() {
        return spawnDimension;
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

        private DimensionType spawnDimension;
        private int veinSize;
        private int veinsPerChunk;
        private int minY;
        private int maxY;

        public Properties(int veinSize, int veinsPerChunk, int minY, int maxY) {
            this.spawnDimension = DimensionType.OVERWORLD;
            this.veinSize = veinSize;
            this.veinsPerChunk = veinsPerChunk;
            this.minY = minY;
            this.maxY = maxY;
        }

        public Properties spawnWorld(DimensionType spawnDimension) {
            this.spawnDimension = spawnDimension;
            return this;
        }

    }

}
