package com.tyrellplayz.zlib.event;

import net.minecraft.resources.IResourceManager;
import net.minecraft.resources.IResourceManagerReloadListener;
import net.minecraft.resources.ResourcePackType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.resource.IResourceType;
import net.minecraftforge.resource.ISelectiveResourceReloadListener;

import java.util.function.Predicate;


/**
 * Fired whenever an event involving reloading resources occurs.
 */
public class ServerResourcesReloadedEvent extends Event {

    private final IResourceManager resourceManager;

    public ServerResourcesReloadedEvent(IResourceManager resourceManager) {
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
        return ResourcePackType.SERVER_DATA;
    }


    public static class ServerReloadListener implements IResourceManagerReloadListener {

        public ServerReloadListener() { }

        @Override
        public void onResourceManagerReload(IResourceManager resourceManager) {
            MinecraftForge.EVENT_BUS.post(new ServerResourcesReloadedEvent(resourceManager));
        }

    }

}