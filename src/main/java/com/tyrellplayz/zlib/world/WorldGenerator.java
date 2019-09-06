package com.tyrellplayz.zlib.world;

import com.tyrellplayz.zlib.world.ore.Ore;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
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

        Collection<Ore> ores = RegistryManager.ACTIVE.getRegistry(Ore.class).getValues();

        if(ores.isEmpty()) return;

        for(Biome biome : Registry.BIOME) {
            addToBiome(biome,ores);
        }
    }

    @SuppressWarnings("ConstantConditions")
    private static void addToBiome(Biome biome, Collection<Ore> ores) {
        if(checkedBiomes.contains(biome)) return;
        checkedBiomes.add(biome);

        for (Ore ore : ores) {
            Collection<Ore.SpawnWorld> spawnWorlds = ore.getSpawnWorlds();

            if(spawnWorlds.contains(Ore.SpawnWorld.OVERWORLD)) {
                if(biome.getCategory() != Biome.Category.NETHER || biome.getCategory() != Biome.Category.THEEND) addOre(biome, ore.getFillerBlockType(), ore);
            }

            if(spawnWorlds.contains(Ore.SpawnWorld.NETHER)) {
                if(biome.getCategory() == Biome.Category.NETHER) addOre(biome, ore.getFillerBlockType(), ore);
            }

            if(spawnWorlds.contains(Ore.SpawnWorld.THEEND)) {
                if(biome.getCategory() == Biome.Category.THEEND) addOre(biome, ore.getFillerBlockType(), ore);
            }

        }

    }

    private static void addOre(Biome biome, OreFeatureConfig.FillerBlockType target, Ore ore) {
        biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(Feature.ORE,
                new OreFeatureConfig(target, ore.getBlock().getDefaultState(), ore.getVeinSize()), Placement.COUNT_RANGE,
                new CountRangeConfig(ore.getVeinsPerChunk(),ore.getMinY(),ore.getMinY(),ore.getMaxY())));
    }

}
