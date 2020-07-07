package com.tyrellplayz.zlib.network;

import com.tyrellplayz.zlib.network.messages.Message;
import com.tyrellplayz.zlib.util.Util;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class NetworkManager {
    private static final Logger LOGGER = LogManager.getLogger();

    public final ResourceLocation name;
    public final String protocolVersion;

    private final SimpleChannel CHANNEL;

    private int messageId;

    public NetworkManager(String modId) {
        this(modId,"1");
    }

    public NetworkManager(String modId, String protocolVersion) {
        this.name = new ResourceLocation(modId,"main_channel");
        this.protocolVersion = protocolVersion;
        CHANNEL = NetworkRegistry.newSimpleChannel(name, () -> protocolVersion, s -> true, s -> true);
    }

    public <T extends Message<T>> void registerMessage(Class<T> messageType) {
        try {
            T instance = messageType.newInstance();
            registerMessage(messageType, Message::writePacket, instance::readPacket, Message::handlePacket);
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public <T extends Message<T>> void registerMessage(Class<T> messageType, BiConsumer<T, PacketBuffer> encoder, Function<PacketBuffer, T> decoder, BiConsumer<T, Supplier<NetworkEvent.Context>> messageConsumer) {
        CHANNEL.registerMessage(messageId++, messageType,encoder,decoder,messageConsumer);
    }

    public SimpleChannel getChannel() {
        return CHANNEL;
    }

    public ResourceLocation getChannelName() {
        return name;
    }

    /**
     * Sends a packet to the server.  Must only be ran on Client-side.
     * @param message The message
     */
    @OnlyIn(Dist.CLIENT)
    public <T extends Message<T>> void sendToServer(Message<T> message) {
        CHANNEL.sendToServer(message);
    }

    /**
     * Sends a packet to a specific player. Must only be ran on server-side.
     * @param message The message.
     * @param player The player.
     */
    public <T extends Message<T>> void sendTo(Message<T> message, ServerPlayerEntity player) {
        if(!(player instanceof FakePlayer)) {
            if(isServerSide()) CHANNEL.send(PacketDistributor.PLAYER.with(() -> player),message);
        }
    }

    /**
     * Sends the packet to all players tracking the given chunk. Must only be ran on server-side.
     * @param message The message.
     * @param chunk The chunk.
     * @param <T>
     */
    public <T extends Message<T>> void sendToChunk(Message<T> message, Chunk chunk) {
        if(isServerSide()) CHANNEL.send(PacketDistributor.TRACKING_CHUNK.with(() -> chunk),message);
    }

    /**
     * Sends the packet to all players connected to the server. Must only be ran on server-side.
     * @param message The message
     */
    public <T extends Message<T>> void sendToAll(Message<T> message) {
        if(isServerSide()) CHANNEL.send(PacketDistributor.ALL.noArg(),message);
    }

    public boolean isServerSide() {
        if(Thread.currentThread().getName().equals("Render thread")) {
            LOGGER.error("Cannot send packets from a server if you are on a client.");
            return false;
        }
        return true;
    }

}
