package com.tyrellplayz.zlib.data;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;

import java.util.function.Consumer;

public abstract class RecipeProvider extends net.minecraft.data.RecipeProvider {

    public RecipeProvider(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected abstract void registerRecipes(Consumer<IFinishedRecipe> consumer);

}
