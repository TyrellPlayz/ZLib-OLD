package com.tyrellplayz.zlib.world;

import com.tyrellplayz.zlib.ZLib;
import com.tyrellplayz.zlib.world.ore.Ore;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.registries.RegistryManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class WorldGenerator {

    private static List<Biome> checkedBiomes = new ArrayList<>();

    public static void initFeatures() {

        // Get a collection of all the ores that have been registered.
        Collection<Ore> ores = RegistryManager.ACTIVE.getRegistry(Ore.class).getValues();

        // If there are no registered ores, just return.
        if(ores.isEmpty()) {
            System.out.println("There are no registered ores.");
            return;
        }

        for(Biome biome : Registry.BIOME) {
            addOresToBiome(biome,ores);
        }

    }

    @SuppressWarnings("ConstantConditions")
    private static void addOresToBiome(Biome biome, Collection<Ore> ores) {
        if(checkedBiomes.contains(biome)) return;
        checkedBiomes.add(biome);

        for (Ore ore : ores) {
            if(ore.getBiomes().contains(biome.getRegistryName())) {
                ZLib.LOGGER.debug("Adding "+ore.getRegistryName()+" to biome "+biome.getRegistryName());
                addOre(biome,ore.getType(),ore);
            }
        }
    }

    private static void addOre(Biome biome, OreFeatureConfig.FillerBlockType target, Ore ore) {
        OreFeatureConfig config = new  OreFeatureConfig(target, ore.getBlockState(), ore.getVeinSize());
        CountRangeConfig rangeConfig = new CountRangeConfig(ore.getVeinsPerChunk(),ore.getMinY(),ore.getMinY(),ore.getMaxY());
        biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.func_225566_b_(config).func_227228_a_(Placement.COUNT_RANGE.func_227446_a_(rangeConfig)));
    }



}
