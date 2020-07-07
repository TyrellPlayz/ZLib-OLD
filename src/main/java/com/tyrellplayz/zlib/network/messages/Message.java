package com.tyrellplayz.zlib.network.messages;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.SidedProvider;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public abstract class Message<T extends Message<T>> {

    private final String name;

    public Message(String name) {
        this.name = name;
    }

    /**
     * Writes the raw packet data to the data stream.
     */
    public abstract void writePacket(PacketBuffer buf);

    /**
     * Reads the raw packet data from the data stream.
     */
    public abstract T readPacket(PacketBuffer buf);

    /**
     * Handles the packet when received.
     */
    public abstract void handlePacket(Supplier<NetworkEvent.Context> context);
    
    public String getName() {
        return name;
    }
}
