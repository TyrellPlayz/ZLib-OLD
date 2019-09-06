package com.tyrellplayz.zlib;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.IDataProvider;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public abstract class ZMod {

    protected List<Function<DataGenerator, IDataProvider>> dataProviders = new ArrayList<>();

    public ZMod() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onCommonSetup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onClientSetup);

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::dataGeneratorSetup);
    }

    public abstract void onCommonSetup(final FMLCommonSetupEvent event);

    public abstract void onClientSetup(final FMLClientSetupEvent event);

    protected void addDataProvider(Function<DataGenerator, IDataProvider> dataProvider) {
        dataProviders.add(dataProvider);
    }

    public void dataGeneratorSetup(final GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        dataProviders.forEach(dataProvider -> {
            IDataProvider provider = dataProvider.apply(generator);
            event.getGenerator().addProvider(provider);
        });
    }

}
