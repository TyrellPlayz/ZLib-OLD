package com.tyrellplayz.zlib.util;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.network.PacketBuffer;

import java.util.ArrayList;
import java.util.List;

public class PacketUtil {

    private PacketUtil() {}

    /**
     * Writes the given list to the buffer as strings.
     */
    public static <T> PacketBuffer writeList(PacketBuffer packetBuffer, List<T> list) {
        packetBuffer.writeVarInt(list.size());
        list.forEach(object -> packetBuffer.writeString(object.toString()));
        return packetBuffer;
    }

    /**
     * Reads a string list from the buffer.
     * @return The list of strings.
     */
    public static List<String> readStringList(PacketBuffer packetBuffer) {
        List<String> list = new ArrayList<>();
        int listSize = packetBuffer.readVarInt();
        for (int i = 0; i < listSize; i++) list.add(packetBuffer.readString());
        return list;
    }

    /**
     * Writes the given integer list to the buffer.
     */
    public static PacketBuffer writeIntegerList(PacketBuffer packetBuffer, List<Integer> list) {
        packetBuffer.writeVarInt(list.size());
        list.forEach(packetBuffer::writeVarInt);
        return packetBuffer;
    }

    public static List<Integer> readIntegerList(PacketBuffer packetBuffer) {
        List<Integer> list = new ArrayList<>();
        int listSize = packetBuffer.readVarInt();
        for (int i = 0; i < listSize; i++) list.add(packetBuffer.readVarInt());
        return list;
    }

    public static List<JsonObject> readJsonObjectList(PacketBuffer packetBuffer) {
        List<JsonObject> list = new ArrayList<>();
        int listSize = packetBuffer.readVarInt();
        for (int i = 0; i < listSize; i++) list.add(new JsonParser().parse(packetBuffer.readString()).getAsJsonObject());
        return list;
    }

}
