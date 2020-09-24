package com.tyrellplayz.zlib.world;

import com.tyrellplayz.zlib.world.ore.OreType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.template.RuleTest;
import net.minecraft.world.gen.placement.ConfiguredPlacement;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.placement.TopSolidRangeConfig;
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
            if(ore.isValidBiome(biome)) addOre(biome,ore);
        }));

    }

    private static void addOre(Biome biome, OreType oreType) {
        OreFeatureConfig config = new OreFeatureConfig(oreType.getRuleTest(),oreType.getBlockState(),oreType.getMaxVeinSize());
        ConfiguredFeature<?,?> feature = Feature.ORE
                .withConfiguration(config)
                .withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(oreType.getMinHeight(),oreType.getMinHeight(), oreType.getMaxHeight())))
                .spreadHorizontally()
                .repeat(oreType.getPerChunk());
        new BiomeModifier(biome).addFeature(GenerationStage.Decoration.UNDERGROUND_ORES,feature);
    }

}
