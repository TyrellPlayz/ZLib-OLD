package com.tyrellplayz.zlib.world.ore;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.dimension.OverworldDimension;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraftforge.registries.ForgeRegistryEntry;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Holds all the information to generate ore in a world.
 * TODO: Add support for custom biomes.
 * @author TyrellPlayz
 * @since 0.1.0
 */
public class Ore extends ForgeRegistryEntry<Ore> {

    private final BlockState blockState;
    private final Collection<ResourceLocation> biomes;
    private OreFeatureConfig.FillerBlockType type;

    private final int veinSize;
    private final int veinsPerChunk;
    private final int minY;
    private final int maxY;

    public Ore(BlockState blockState, Ore.Properties properties) {
        this.blockState = blockState;
        this.biomes = properties.biomes;
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

    public Collection<ResourceLocation> getBiomes() {
        return biomes;
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

        private List<ResourceLocation> biomes = new ArrayList<>();
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

        public Properties addBiome(ResourceLocation biome) {
            this.biomes.add(biome);
            return this;
        }

        public Properties addBiome(Biome biome) {
            this.biomes.add(biome.getRegistryName());
            return this;
        }

        public Properties removeBiome(ResourceLocation biome) {
            this.biomes.remove(biome);
            return this;
        }

        public Properties removeBiome(Biome biome) {
            this.biomes.remove(biome.getRegistryName());
            return this;
        }

        public Properties overworld() {
            Collections.addAll(this.biomes, Biomes.OCEAN.getRegistryName(), Biomes.PLAINS.getRegistryName(), Biomes.DESERT.getRegistryName(), Biomes.MOUNTAINS.getRegistryName(),
                    Biomes.FOREST.getRegistryName(), Biomes.TAIGA.getRegistryName(), Biomes.SWAMP.getRegistryName(), Biomes.RIVER.getRegistryName(), Biomes.FROZEN_OCEAN.getRegistryName(),
                    Biomes.FROZEN_RIVER.getRegistryName(), Biomes.SNOWY_TUNDRA.getRegistryName(), Biomes.SNOWY_MOUNTAINS.getRegistryName(), Biomes.MUSHROOM_FIELDS.getRegistryName(),
                    Biomes.MUSHROOM_FIELD_SHORE.getRegistryName(), Biomes.BEACH.getRegistryName(), Biomes.DESERT_HILLS.getRegistryName(), Biomes.WOODED_HILLS.getRegistryName(),
                    Biomes.TAIGA_HILLS.getRegistryName(), Biomes.MOUNTAIN_EDGE.getRegistryName(), Biomes.JUNGLE.getRegistryName(), Biomes.JUNGLE_HILLS.getRegistryName(),
                    Biomes.JUNGLE_EDGE.getRegistryName(), Biomes.DEEP_OCEAN.getRegistryName(), Biomes.STONE_SHORE.getRegistryName(), Biomes.SNOWY_BEACH.getRegistryName(),
                    Biomes.BIRCH_FOREST.getRegistryName(), Biomes.BIRCH_FOREST_HILLS.getRegistryName(), Biomes.DARK_FOREST.getRegistryName(), Biomes.SNOWY_TAIGA.getRegistryName(),
                    Biomes.SNOWY_TAIGA_HILLS.getRegistryName(), Biomes.GIANT_TREE_TAIGA.getRegistryName(), Biomes.GIANT_TREE_TAIGA_HILLS.getRegistryName(), Biomes.WOODED_MOUNTAINS.getRegistryName(),
                    Biomes.SAVANNA.getRegistryName(), Biomes.SAVANNA_PLATEAU.getRegistryName(), Biomes.BADLANDS.getRegistryName(), Biomes.WOODED_BADLANDS_PLATEAU.getRegistryName(),
                    Biomes.BADLANDS_PLATEAU.getRegistryName(), Biomes.WARM_OCEAN.getRegistryName(), Biomes.LUKEWARM_OCEAN.getRegistryName(), Biomes.COLD_OCEAN.getRegistryName(),
                    Biomes.DEEP_WARM_OCEAN.getRegistryName(),
                    Biomes.DEEP_LUKEWARM_OCEAN.getRegistryName(), Biomes.DEEP_COLD_OCEAN.getRegistryName(), Biomes.DEEP_FROZEN_OCEAN.getRegistryName(), Biomes.THE_VOID.getRegistryName(),
                    Biomes.SUNFLOWER_PLAINS.getRegistryName(), Biomes.DESERT_LAKES.getRegistryName(), Biomes.GRAVELLY_MOUNTAINS.getRegistryName(), Biomes.FLOWER_FOREST.getRegistryName(),
                    Biomes.TAIGA_MOUNTAINS.getRegistryName(), Biomes.SWAMP_HILLS.getRegistryName(), Biomes.ICE_SPIKES.getRegistryName(), Biomes.MODIFIED_JUNGLE.getRegistryName(),
                    Biomes.MODIFIED_JUNGLE_EDGE.getRegistryName(), Biomes.TALL_BIRCH_FOREST.getRegistryName(), Biomes.TALL_BIRCH_HILLS.getRegistryName(), Biomes.DARK_FOREST_HILLS.getRegistryName(),
                    Biomes.SNOWY_TAIGA_MOUNTAINS.getRegistryName(), Biomes.GIANT_SPRUCE_TAIGA.getRegistryName(), Biomes.GIANT_SPRUCE_TAIGA_HILLS.getRegistryName(),
                    Biomes.MODIFIED_GRAVELLY_MOUNTAINS.getRegistryName(), Biomes.SHATTERED_SAVANNA.getRegistryName(), Biomes.SHATTERED_SAVANNA_PLATEAU.getRegistryName(),
                    Biomes.ERODED_BADLANDS.getRegistryName(), Biomes.MODIFIED_WOODED_BADLANDS_PLATEAU.getRegistryName(), Biomes.MODIFIED_BADLANDS_PLATEAU.getRegistryName(),
                    Biomes.BAMBOO_JUNGLE.getRegistryName(), Biomes.BAMBOO_JUNGLE_HILLS.getRegistryName());
            return this;
        }

        public Properties nether() {
            biomes.add(Biomes.NETHER.getRegistryName());
            return this;
        }

        public Properties theEnd() {
            biomes.add(Biomes.THE_END.getRegistryName());
            biomes.add(Biomes.SMALL_END_ISLANDS.getRegistryName());
            biomes.add(Biomes.END_BARRENS.getRegistryName());
            biomes.add(Biomes.END_HIGHLANDS.getRegistryName());
            biomes.add(Biomes.END_MIDLANDS.getRegistryName());
            return this;
        }

    }

}
