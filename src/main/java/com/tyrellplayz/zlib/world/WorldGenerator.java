package com.tyrellplayz.zlib.world;

import com.tyrellplayz.zlib.world.ore.OreType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryManager;

import java.util.Collection;

public class WorldGenerator {

    public static void initFeatures() {

        // Get a collection of all the ores that have been registered.
        final Collection<OreType> ORES = RegistryManager.ACTIVE.getRegistry(OreType.class).getValues();
        // If there are no registered ores, just return.
        if(ORES.isEmpty()) return;

        ForgeRegistries.BIOMES.forEach(biome -> ORES.forEach(ore -> {
            if(ore.canSpawn(biome)) addOre(biome,ore.getType(),ore);
        }));
    }

    private static void addOre(Biome biome, OreFeatureConfig.FillerBlockType target, OreType ore) {
        OreFeatureConfig config = new  OreFeatureConfig(target, ore.getBlockState(), ore.getMaxVeinSize());
        CountRangeConfig rangeConfig = new CountRangeConfig(ore.getPerChunk(),ore.getBottomOffset(),ore.getTopOffset(),ore.getMaxHeight());
        biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(config).withPlacement(Placement.COUNT_RANGE.configure(rangeConfig)));
    }



}
