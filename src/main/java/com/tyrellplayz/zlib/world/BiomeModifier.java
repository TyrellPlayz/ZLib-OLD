package com.tyrellplayz.zlib.world;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.ConfiguredFeature;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * https://github.com/BluSunrize/ImmersiveEngineering/blob/1.16.2/src/main/java/blusunrize/immersiveengineering/common/world/BiomeModifier.java
 */

public class BiomeModifier {

    private final Biome biome;

    public BiomeModifier(Biome biome) {
        this.biome = biome;
    }

    public void addFeature(GenerationStage.Decoration stage, ConfiguredFeature<?,?> feature) {
        final int index = stage.ordinal();
        List<List<Supplier<ConfiguredFeature<?,?>>>> allFeatures = new ArrayList<>(biome.getBiomeGenerationSettings().field_242484_f);
        while (allFeatures.size() <= index) {
            allFeatures.add(new ArrayList<>());
        }
        List<Supplier<ConfiguredFeature<?,?>>> oreGen = new ArrayList<>(allFeatures.get(index));
        oreGen.add(() -> feature);
        allFeatures.set(index,oreGen);
        biome.getBiomeGenerationSettings().field_242484_f = allFeatures;
    }

}
