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

        /*
        for (Ore ore : ores) {
            if(ore.getBiomes().isEmpty()) ZLib.LOGGER.info("The ore "+ore.getRegistryName()+" has no biomes assigned to it, so it wont generate.");
            for (Biome biome : ore.getBiomes()) {
                addOreToBiome(biome,ore);
            }
        }
        */
    }

    @SuppressWarnings("ConstantConditions")
    private static void addOresToBiome(Biome biome, Collection<Ore> ores) {
        if(checkedBiomes.contains(biome)) return;
        checkedBiomes.add(biome);

        for (Ore ore : ores) {
            if(ore.getBiomes().contains(biome.getRegistryName())) {
                System.out.println("Adding "+ore.getRegistryName()+" to biome "+biome.getRegistryName());
                addOre(biome,ore.getType(),ore);
            }
        }

        /*
        if(spawnWorld.equals(DimensionType.THE_NETHER)) {
            if(biome.getCategory() == Biome.Category.NETHER) addOre(biome, OreFeatureConfig.FillerBlockType.NETHERRACK, ore);
            //}else if (spawnWorld.equals(Ore.SpawnWorld.THEEND)) {
            //    if(biome.getCategory() == Biome.Category.THEEND) addOre(biome, ore.getFillerBlockType(), ore);
        }else {
            if(biome.getCategory() != Biome.Category.NETHER || biome.getCategory() != Biome.Category.THEEND) addOre(biome, OreFeatureConfig.FillerBlockType.NATURAL_STONE, ore);
        }
         */
    }

    private static void addOre(Biome biome, OreFeatureConfig.FillerBlockType target, Ore ore) {
        biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(Feature.ORE,
                new OreFeatureConfig(target, ore.getBlockState(), ore.getVeinSize()), Placement.COUNT_RANGE,
                new CountRangeConfig(ore.getVeinsPerChunk(),ore.getMinY(),ore.getMinY(),ore.getMaxY())));
    }

}
