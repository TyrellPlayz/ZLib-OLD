package com.tyrellplayz.zlib;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.IDataProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.IForgeRegistryEntry;
import net.minecraftforge.registries.RegistryBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public abstract class ZMod {

    public final String MOD_ID;

    private final List<Function<DataGenerator, IDataProvider>> dataProviders = new ArrayList<>();
    private final List<RegistryBuilder<? extends IForgeRegistryEntry<?>>> registryBuilders = new ArrayList<>();

    public ZMod(String modId) {
        this.MOD_ID = modId;

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onCommonSetup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onClientSetup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::loadComplete);

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::registerRegistries);

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::dataGeneratorSetup);

        MinecraftForge.EVENT_BUS.addListener(this::registerRegistries);
    }

    public void onCommonSetup(final FMLCommonSetupEvent event) { }

    public void onClientSetup(final FMLClientSetupEvent event) { }

    public void loadComplete(final FMLLoadCompleteEvent event) { }

    protected final void addDataProvider(Function<DataGenerator, IDataProvider> dataProvider) {
        dataProviders.add(dataProvider);
    }

    public <T extends IForgeRegistryEntry<T>> void createRegistry(ResourceLocation key, Class<T> type) {
        registryBuilders.add(new RegistryBuilder<T>().setName(key).setType(type).setDefaultKey(key));
    }

    public void registerRegistries(RegistryEvent.NewRegistry event) {
        System.out.println("Creating Registries");
        registryBuilders.forEach(RegistryBuilder::create);
    }

    public void dataGeneratorSetup(final GatherDataEvent event) {

    }

}
