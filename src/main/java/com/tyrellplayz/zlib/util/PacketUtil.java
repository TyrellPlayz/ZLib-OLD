package com.tyrellplayz.zlib.util;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.network.PacketBuffer;

import java.util.*;
import java.util.function.Function;

public class PacketUtil {

    private PacketUtil() {}

    /**
     * Writes the given list to the buffer as strings.
     */
    public static <T> PacketBuffer writeList(PacketBuffer packetBuffer, Collection<T> list) {
        packetBuffer.writeVarInt(list.size());
        list.forEach(object -> packetBuffer.writeString(object.toString()));
        return packetBuffer;
    }

    public static <T> PacketBuffer writeList(PacketBuffer packetBuffer, Collection<T> list, Function<T,String> toStringFunc) {
        packetBuffer.writeVarInt(list.size());
        list.forEach(object -> packetBuffer.writeString(toStringFunc.apply(object)));
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


    public static <K,V> PacketBuffer writeMap(PacketBuffer packetBuffer, Map<K,V> map) {
        Set<K> keySet = map.keySet();
        int size = keySet.size();
        packetBuffer.writeVarInt(size);
        map.forEach((k, v) -> {
            packetBuffer.writeString(k.toString());
            packetBuffer.writeString(packetBuffer.toString());
        });
        return packetBuffer;
    }

    public static <K,V> PacketBuffer writeMap(PacketBuffer packetBuffer, Map<K,V> map, Function<K,String> keyToStringFunc, Function<V,String> valueToStringFunc) {
        packetBuffer.writeVarInt(map.size());
        map.forEach((k, v) -> {
            packetBuffer.writeString(keyToStringFunc.apply(k));
            packetBuffer.writeString(valueToStringFunc.apply(v));
        });
        return packetBuffer;
    }

    public static Map<String,String> readStringStringMap(PacketBuffer packetBuffer) {
        Map<String,String> stringMap = new HashMap<>();
        int size = packetBuffer.readVarInt();
        for (int i = 0; i < size; i++) {
            String key = packetBuffer.readString();
            String value = packetBuffer.readString();
            stringMap.put(key,value);
        }
        return stringMap;
    }

    public static Map<String,JsonObject> readStringJsonMap(PacketBuffer packetBuffer) {
        Map<String,JsonObject> stringJsonObjectMap = new HashMap<>();
        int size = packetBuffer.readVarInt();
        for (int i = 0; i < size; i++) {
            String key = packetBuffer.readString();
            String value = packetBuffer.readString();
            stringJsonObjectMap.put(key,new JsonParser().parse(value).getAsJsonObject());
        }
        return stringJsonObjectMap;
    }

}
