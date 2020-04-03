package com.tyrellplayz.zlib.network;

import com.tyrellplayz.zlib.network.messages.Message;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class NetworkManager {

    public final ResourceLocation name;
    public final String protocolVersion;

    private final SimpleChannel CHANNEL;

    public NetworkManager(String modId) {
        this(modId,"1");
    }

    public NetworkManager(String modId, String protocolVersion) {
        this.name = new ResourceLocation(modId,"main_channel");
        this.protocolVersion = protocolVersion;
        CHANNEL = NetworkRegistry.newSimpleChannel(name, () -> protocolVersion, s -> true, s -> true);
    }

    public <T extends Message<T>> void registerMessage(int index, Class<T> messageType, BiConsumer<T, PacketBuffer> encoder, Function<PacketBuffer, T> decoder, BiConsumer<T, Supplier<NetworkEvent.Context>> messageConsumer) {
        CHANNEL.registerMessage(index, messageType,encoder,decoder,messageConsumer);
    }

    public SimpleChannel getChannel() {
        return CHANNEL;
    }

    public ResourceLocation getChannelName() {
        return name;
    }

    /**
     * Sends a packet to the server. Must be called Client side only.
     * @param message
     */
    public <T extends Message<T>> void sendToServer(Message<T> message) {
        CHANNEL.sendToServer(message);
    }

    /**
     * Sends a packet to a specific player. Must be called Server side only.
     * @param message
     * @param player
     */
    public <T extends Message<T>> void sendTo(Message<T> message, ServerPlayerEntity player) {
        if(!(player instanceof FakePlayer)) {
            CHANNEL.sendTo(message,player.connection.netManager, NetworkDirection.PLAY_TO_CLIENT);
        }
    }

    public <T extends Message<T>> void sendToAll(Message<T> message, MinecraftServer server) {
        for (ServerPlayerEntity player : server.getPlayerList().getPlayers()) {
            sendTo(message,player);
        }
    }

}
