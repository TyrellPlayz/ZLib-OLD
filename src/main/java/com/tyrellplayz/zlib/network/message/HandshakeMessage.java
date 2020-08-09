package com.tyrellplayz.zlib.network.message;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.FMLHandshakeHandler;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.BiConsumer;
import java.util.function.IntSupplier;
import java.util.function.Supplier;

public abstract class HandshakeMessage<T extends Message<T>> extends Message<T> implements IntSupplier {

    private int loginIndex;

    public HandshakeMessage(String name) {
        super(name);
    }

    public void setLoginIndex(int loginIndex) {
        this.loginIndex = loginIndex;
    }

    public int getLoginIndex() {
        return loginIndex;
    }

    @Override
    public int getAsInt() {
        return getLoginIndex();
    }

    public abstract boolean isLoginPacket();

    public static class ClientToServerAcknowledge extends HandshakeMessage<ClientToServerAcknowledge> {

        public ClientToServerAcknowledge() {
            super("client_to_server_acknowledge");
        }

        @Override
        public void writePacket(PacketBuffer buf) {

        }

        @Override
        public ClientToServerAcknowledge readPacket(PacketBuffer buf) {
            return new ClientToServerAcknowledge();
        }

        @Override
        public BiConsumer<ClientToServerAcknowledge, Supplier<NetworkEvent.Context>> handlePacket() {
            return FMLHandshakeHandler.indexFirst((fmlHandshakeHandler, clientToServerAcknowledge, contextSupplier) -> {
                contextSupplier.get().setPacketHandled(true);
            });
        }

        @Override
        public boolean isLoginPacket() {
            return false;
        }
    }

}
