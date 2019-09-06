package com.tyrellplayz.zlib.registry;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains methods to register Entities.
 */
public abstract class EntityRegistry {

    protected static final List<EntityType> ENTITY_TYPES = new ArrayList<>();

    /**
     * Creates and Registers a new {@link EntityType}.
     * @param id The id of the {@link Entity}.
     * @param factory A new instance of the Entities class (Eg MyEntity::new).
     * @param classification The type of entity the {@link Entity} is.
     * @param width The width of the {@link Entity}.
     * @param height The height of the {@link Entity}.
     * @param <T> The class of the {@link Entity}.
     * @return The created {@link EntityType}.
     */
    protected static <T extends Entity> EntityType<T> register(String id, EntityType.IFactory<T> factory, EntityClassification classification, float width, float height) {
        return register(new ResourceLocation(id),EntityType.Builder.create(factory,classification).size(width,height));
    }

    /**
     * Creates and Registers a new {@link EntityType}.
     * @param id The id of the {@link Entity}.
     * @param factory A new instance of the Entities class (Eg MyEntity::new).
     * @param classification The type of entity the {@link Entity} is.
     * @param width The width of the {@link Entity}.
     * @param height The height of the {@link Entity}.
     * @param <T> The class of the {@link Entity}.
     * @return The created {@link EntityType}.
     */
    protected static <T extends Entity> EntityType<T> register(ResourceLocation id, EntityType.IFactory<T> factory, EntityClassification classification, float width, float height) {
        return register(id,EntityType.Builder.create(factory,classification).size(width,height));
    }

    /**
     * Creates and Registers a new {@link EntityType}.
     * @param id The id of the {@link Entity}.
     * @param builder The builder.
     * @param <T> The class of the {@link Entity}.
     * @return The created {@link EntityType}.
     */
    protected static <T extends Entity> EntityType<T> register(String id, EntityType.Builder<T> builder) {
        return register(new ResourceLocation(id),builder);
    }

    /**
     * Creates and Registers a new {@link EntityType}.
     * @param id The id of the {@link Entity}.
     * @param builder The builder.
     * @param <T> The class of the {@link Entity}.
     * @return The created {@link EntityType}.
     */
    protected static <T extends Entity> EntityType<T> register(ResourceLocation id, EntityType.Builder<T> builder) {
        EntityType<T> type = builder.build(id.toString());
        type.setRegistryName(id);
        ENTITY_TYPES.add(type);
        return type;
    }

    @SubscribeEvent
    public static void registerTypes(final RegistryEvent.Register<EntityType<?>> event) {
        ENTITY_TYPES.forEach(entityType -> event.getRegistry().register(entityType));
    }

}
