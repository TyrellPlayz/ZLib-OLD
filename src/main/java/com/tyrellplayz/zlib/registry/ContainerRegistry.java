package com.tyrellplayz.zlib.registry;

import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.apache.commons.lang3.Validate;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains methods to register Containers.
 */
public abstract class ContainerRegistry {

    protected static final List<ContainerType<?>> CONTAINERS = new ArrayList<>();

    /**
     * Creates and Registers a new {@link ContainerType}.
     * @param id The id of the {@link ContainerType}.
     * @param factory A new instance of the Containers class (Eg MyContainer::new).
     * @param <T> The class of the {@link Container}.
     * @return The created {@link ContainerType}.
     */
    protected static <T extends Container> ContainerType<T> register(String id, ContainerType.IFactory<T> factory) {
        return register(new ResourceLocation(id), factory);
    }

    /**
     * Creates and Registers a new {@link ContainerType}.
     * @param id The id of the {@link ContainerType}.
     * @param factory A new instance of the Containers class (Eg MyContainer::new).
     * @param <T> The class of the {@link Container}.
     * @return The created {@link ContainerType}.
     */
    protected static <T extends Container> ContainerType<T> register(ResourceLocation id, ContainerType.IFactory<T> factory) {
        Validate.notNull(id, "Container should have a registry name");
        Validate.notNull(factory, "Container factory is null for " + id.toString());

        ContainerType<T> containerType = new ContainerType<>(factory);
        containerType.setRegistryName(id);
        CONTAINERS.add(containerType);
        return containerType;
    }

    @SubscribeEvent
    public static void registerContainers(RegistryEvent.Register<ContainerType<?>> event) {
        CONTAINERS.forEach((type) -> event.getRegistry().register(type));
    }

}
