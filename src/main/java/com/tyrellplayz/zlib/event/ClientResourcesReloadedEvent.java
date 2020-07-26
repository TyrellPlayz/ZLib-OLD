package com.tyrellplayz.zlib.event;

import net.minecraft.resources.IResourceManager;
import net.minecraft.resources.ResourcePackType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.resource.IResourceType;
import net.minecraftforge.resource.ISelectiveResourceReloadListener;

import java.util.function.Predicate;


/**
 * Fired whenever an event involving reloading client resources occurs.
 */
@OnlyIn(Dist.CLIENT)
public class ClientResourcesReloadedEvent extends Event {

    private final IResourceManager resourceManager;

    public ClientResourcesReloadedEvent(IResourceManager resourceManager) {
        this.resourceManager = resourceManager;
    }

    /**
     * @return The resource manager that was reloaded.
     */
    public IResourceManager getResourceManager() {
        return resourceManager;
    }

    /**
     * @return The type of pack being used.
     */
    public ResourcePackType getPackType() {
        return ResourcePackType.CLIENT_RESOURCES;
    }

    @OnlyIn(Dist.CLIENT)
    public static class ClientReloadListener implements ISelectiveResourceReloadListener {
        public ClientReloadListener() { }

        @Override
        public void onResourceManagerReload(IResourceManager resourceManager, Predicate<IResourceType> resourcePredicate) {
            MinecraftForge.EVENT_BUS.post(new ClientResourcesReloadedEvent(resourceManager));
        }

    }

}