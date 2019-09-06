package com.tyrellplayz.zlib.world.ore;

import net.minecraft.block.Block;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraftforge.registries.ForgeRegistryEntry;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class Ore extends ForgeRegistryEntry<Ore> {

    private final Block block;
    private final OreFeatureConfig.FillerBlockType fillerBlockType;
    private final SpawnWorld spawnWorld;

    private final int veinSize;
    private final int veinsPerChunk;
    private final int minY;
    private final int maxY;

    public Ore(Block block, Ore.Properties properties) {
        this.block = block;
        this.fillerBlockType = properties.fillerBlockType;
        this.spawnWorld = properties.spawnWorld;
        this.veinSize = properties.veinSize;
        this.veinsPerChunk = properties.veinsPerChunk;
        this.minY = properties.minY;
        this.maxY = properties.maxY;
    }

    public Block getBlock() {
        return block;
    }

    public OreFeatureConfig.FillerBlockType getFillerBlockType() {
        return fillerBlockType;
    }

    public SpawnWorld getSpawnWorld() {
        return spawnWorld;
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

        private OreFeatureConfig.FillerBlockType fillerBlockType;
        private SpawnWorld spawnWorld;
        private int veinSize;
        private int veinsPerChunk;
        private int minY;
        private int maxY;

        public Properties(int veinSize, int veinsPerChunk, int minY, int maxY) {
            this.fillerBlockType = OreFeatureConfig.FillerBlockType.NATURAL_STONE;
            this.spawnWorld = SpawnWorld.OVERWORLD;
            this.veinSize = veinSize;
            this.veinsPerChunk = veinsPerChunk;
            this.minY = minY;
            this.maxY = maxY;
        }

        public Properties filterBlockType(OreFeatureConfig.FillerBlockType fillerBlockType) {
            this.fillerBlockType = fillerBlockType;
            return this;
        }

        public Properties spawnWorlds(SpawnWorld spawnWorld) {
            this.spawnWorld = spawnWorld;
            return this;
        }

    }

    public enum SpawnWorld {
        OVERWORLD("overworld"),
        THEEND("the_end"),
        NETHER("nether");

        private static final Map<String, SpawnWorld> BY_NAME = Arrays.stream(values()).collect(Collectors.toMap(Ore.SpawnWorld::getName, (category) -> category));
        private final String name;

        SpawnWorld(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }
    }

}
