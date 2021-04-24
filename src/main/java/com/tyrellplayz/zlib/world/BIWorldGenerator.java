package com.tyrellplayz.zlib.world;

import com.tyrellplayz.zlib.world.ore.OreType;
import net.minecraft.block.OreBlock;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.placement.TopSolidRangeConfig;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.RegistryManager;

import java.util.Collection;

public class BIWorldGenerator {

    @SubscribeEvent
    public void onBiomeLoad(BiomeLoadingEvent event) {
        BiomeGenerationSettingsBuilder settings = event.getGeneration();
        getRegisteredOres().forEach(oreType -> {
            settings.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES,createOreFeature(oreType));
        });
    }

    private static ConfiguredFeature<?,?> createOreFeature(OreType oreType) {
        OreFeatureConfig config = new OreFeatureConfig(oreType.getRuleTest(),oreType.getBlockState(),oreType.getMaxVeinSize());

        return Feature.ORE
                .withConfiguration(config)
                .withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(oreType.getMinHeight(),oreType.getMinHeight(), oreType.getMaxHeight())))
                .square()
                .count(oreType.getPerChunk());
    }

    public Collection<OreType> getRegisteredOres() {
        return RegistryManager.ACTIVE.getRegistry(OreType.class).getValues();
    }

}
