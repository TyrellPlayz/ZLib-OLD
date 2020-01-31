package com.tyrellplayz.zlib.event;

import net.minecraft.resources.IResourceManager;
import net.minecraft.resources.IResourceManagerReloadListener;
import net.minecraft.resources.ResourcePackType;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.resource.IResourceType;
import net.minecraftforge.resource.ISelectiveResourceReloadListener;
import net.minecraftforge.resource.ReloadRequirements;

import java.util.function.Predicate;


/**
 * Fired whenever an event involving reloading resources occurs
 */
public abstract class ResourcesReloadedEvent extends Event {

    private final IResourceManager resourceManager;
    private final ResourcePackType packType;

    public ResourcesReloadedEvent(IResourceManager resourceManager, ResourcePackType packType) {
        this.resourceManager = resourceManager;
        this.packType = packType;
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
        return packType;
    }

    /**
     * Fired whenever client resources are reloaded.
     */
    public static class Client extends ResourcesReloadedEvent {

        public Client(IResourceManager resourceManager) {
            super(resourceManager, ResourcePackType.CLIENT_RESOURCES);
        }
    }

    /**
     * Fired whenever Server resources are reloaded.
     */
    public static class Server extends ResourcesReloadedEvent {

        private final MinecraftServer server;

        public Server(IResourceManager resourceManager, MinecraftServer server) {
            super(resourceManager, ResourcePackType.SERVER_DATA);
            this.server = server;
        }

        public MinecraftServer getServer() {
            return server;
        }
    }

    public static class ClientReloadListener implements IResourceManagerReloadListener {
        public ClientReloadListener() { }

        @Override
        public void onResourceManagerReload(IResourceManager resourceManager) {
            MinecraftForge.EVENT_BUS.post(new ResourcesReloadedEvent.Client(resourceManager));
        }

    }

    public static class ServerReloadListener implements IResourceManagerReloadListener {

        private MinecraftServer server;

        public ServerReloadListener(MinecraftServer server) {
            this.server = server;
        }

        @Override
        public void onResourceManagerReload(IResourceManager resourceManager) {
            MinecraftForge.EVENT_BUS.post(new ResourcesReloadedEvent.Server(resourceManager,server));
        }

    }

}