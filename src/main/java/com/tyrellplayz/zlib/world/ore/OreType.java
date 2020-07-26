package com.tyrellplayz.zlib.world.ore;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraftforge.registries.ForgeRegistryEntry;

import java.util.function.Predicate;

/**
 * Holds all the information to generate ore in a world.
 */
public class OreType extends ForgeRegistryEntry<OreType> {

    private final BlockState blockState;
    private final OreFeatureConfig.FillerBlockType type;

    // Chance that the ore generates in a chunk.
    private final int perChunk;
    // The max number of blocks in a vein of ore.
    private final int maxVeinSize;
    // Bottom offset for calculating height that veins can spawn.
    private final int bottomOffset;
    // Top offset for calculating height that veins can spawn.
    private final int topOffset;
    // The base maximum height that veins of ore can spawn.
    // Height is calculated by: random.nextInt(maxHeight - topOffset) + bottomOffset
    private final int maxHeight;

    private final Predicate<Biome> canSpawn;

    public static OreType createOverworldOre(BlockState blockState, int perChunk, int maxVeinSize, int bottomOffset, int topOffset, int maxHeight) {
        return new OreType(blockState,perChunk,maxVeinSize,bottomOffset,topOffset,maxHeight,canSpawnOverworld());
    }

    public static OreType createNetherOre(BlockState blockState, int perChunk, int maxVeinSize, int bottomOffset, int topOffset, int maxHeight) {
        return new OreType(blockState,OreFeatureConfig.FillerBlockType.NETHER_ORE_REPLACEABLES,perChunk,maxVeinSize,bottomOffset,topOffset,maxHeight,canSpawnNether());
    }

    public OreType(BlockState blockState, int perChunk, int maxVeinSize, int bottomOffset, int topOffset, int maxHeight, Predicate<Biome> canSpawn) {
        this(blockState, OreFeatureConfig.FillerBlockType.NATURAL_STONE,perChunk,maxVeinSize,bottomOffset,topOffset,maxHeight,canSpawn);
    }

    public OreType(BlockState blockState, OreFeatureConfig.FillerBlockType type, int perChunk, int maxVeinSize, int bottomOffset, int topOffset, int maxHeight, Predicate<Biome> canSpawn) {
        this.blockState = blockState;
        this.type = type;
        this.perChunk = MathHelper.clamp(perChunk,1,512);
        this.maxVeinSize = MathHelper.clamp(maxVeinSize,1,512);

        this.maxHeight = MathHelper.clamp(maxHeight,1,256);
        this.topOffset = MathHelper.clamp(topOffset,0,this.maxHeight);
        this.bottomOffset = MathHelper.clamp(bottomOffset,0,(256 - this.maxHeight+this.topOffset));

        this.canSpawn = canSpawn;
    }

    public BlockState getBlockState() {
        return blockState;
    }

    public OreFeatureConfig.FillerBlockType getType() {
        return type;
    }

    public int getPerChunk() {
        return perChunk;
    }

    public int getMaxVeinSize() {
        return maxVeinSize;
    }

    public int getBottomOffset() {
        return bottomOffset;
    }

    public int getTopOffset() {
        return topOffset;
    }

    public int getMaxHeight() {
        return maxHeight;
    }

    public boolean canSpawn(Biome biome) {
        return canSpawn.test(biome);
    }

    public static Predicate<Biome> canSpawnOverworld() {
        return biome -> biome.getCategory() != Biome.Category.THEEND && biome.getCategory() != Biome.Category.NETHER;
    }

    public static Predicate<Biome> canSpawnNether() {
        return biome -> biome.getCategory() == Biome.Category.NETHER;
    }

}
