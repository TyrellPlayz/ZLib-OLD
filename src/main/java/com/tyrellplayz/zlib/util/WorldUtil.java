package com.tyrellplayz.zlib.util;

import com.tyrellplayz.zlib.util.helpers.ServerHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.server.ServerWorld;

public class WorldUtil {

    private WorldUtil() {}

    /**
     * Forced loads a chuck and keeps it loaded.
     * @param chunk The chuck to be forced loaded.
     */
    public static void loadChuck(Chunk chunk) {
        World world = chunk.getWorld();
        if(ServerHelper.isServerWorld(world)) {
            ServerWorld serverWorld = (ServerWorld)world;
            serverWorld.forceChunk(chunk.getPos().x,chunk.getPos().z,true);
        }
    }

    /**
     * Unloads a forced loaded chuck.
     * @param chunk The chuck to be forced loaded.
     */
    public static void unloadChuck(Chunk chunk) {
        World world = chunk.getWorld();
        if(ServerHelper.isServerWorld(world)) {
            ServerWorld serverWorld = (ServerWorld)world;
            serverWorld.forceChunk(chunk.getPos().x,chunk.getPos().z,false);
        }
    }

}
