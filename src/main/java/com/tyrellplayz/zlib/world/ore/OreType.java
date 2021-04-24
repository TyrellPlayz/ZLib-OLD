package com.tyrellplayz.zlib.world.ore;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.template.RuleTest;
import net.minecraftforge.registries.ForgeRegistryEntry;

import java.util.function.Predicate;

/**
 * Holds all the information to generate ore in a world.
 */
public class OreType extends ForgeRegistryEntry<OreType> {

    private final BlockState blockState;
    private final RuleTest ruleTest;

    // The average number of ore veins per chunk.
    private final int perChunk;
    // The max number of blocks in a vein of ore.
    private final int maxVeinSize;
    // The minimum height that veins of ore can spawn at.
    private final int minHeight;
    // The maximum height that veins of ore can spawn at.
    private final int maxHeight;

    private final Predicate<Biome> isValidBiome;

    public static OreType createOverworldOre(BlockState blockState, int perChunk, int maxVeinSize, int minHeight, int maxHeight) {
        return new OreType(blockState,perChunk,maxVeinSize,minHeight,maxHeight,canSpawnOverworld());
    }

    public static OreType createNetherOre(BlockState blockState, int perChunk, int maxVeinSize, int minHeight, int maxHeight) {
        return new OreType(blockState,OreFeatureConfig.FillerBlockType.NETHERRACK,perChunk,maxVeinSize,minHeight,maxHeight,canSpawnNether());
    }

    public OreType(BlockState blockState, int perChunk, int maxVeinSize, int minHeight, int maxHeight, Predicate<Biome> canSpawn) {
        this(blockState, OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD,perChunk,maxVeinSize,minHeight,maxHeight,canSpawn);
    }

    public OreType(BlockState blockState, RuleTest ruleTest, int perChunk, int maxVeinSize, int minHeight, int maxHeight, Predicate<Biome> isValidBiome) {
        this.blockState = blockState;
        this.ruleTest = ruleTest;
        this.perChunk = MathHelper.clamp(perChunk,1,512);
        this.maxVeinSize = MathHelper.clamp(maxVeinSize,1,512);

        this.maxHeight = MathHelper.clamp(maxHeight,1,256);
        this.minHeight = MathHelper.clamp(minHeight,0,this.maxHeight);

        this.isValidBiome = isValidBiome;
    }

    public BlockState getBlockState() {
        return blockState;
    }

    public RuleTest getRuleTest() {
        return ruleTest;
    }

    public int getPerChunk() {
        return perChunk;
    }

    public int getMaxVeinSize() {
        return maxVeinSize;
    }

    public int getMinHeight() {
        return minHeight;
    }

    public int getMaxHeight() {
        return maxHeight;
    }

    public boolean isValidBiome(Biome biome) {
        return isValidBiome.test(biome);
    }

    public static Predicate<Biome> canSpawnOverworld() {
        return biome -> biome.getCategory() != Biome.Category.THEEND && biome.getCategory() != Biome.Category.NETHER;
    }

    public static Predicate<Biome> canSpawnNether() {
        return biome -> biome.getCategory() == Biome.Category.NETHER;
    }

    public static Predicate<Biome> canSpawnTheEnd() {
        return biome -> biome.getCategory() == Biome.Category.THEEND;
    };

}
