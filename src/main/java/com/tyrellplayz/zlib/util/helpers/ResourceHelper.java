package com.tyrellplayz.zlib.util.helpers;

import net.minecraft.resources.ResourcePackType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.moddiscovery.ModInfo;
import net.minecraftforge.fml.packs.ModFileResourcePack;
import net.minecraftforge.fml.packs.ResourcePackLoader;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

public class ResourceHelper {

    private ResourceHelper() {}

    public static Optional<InputStream> getModResource(ResourcePackType type, ResourceLocation name) {
        return ModList.get().getMods().stream()
                .map(ModInfo::getModId)
                .map(ResourcePackLoader::getResourcePackFor)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .filter(modFileResourcePack -> modFileResourcePack.resourceExists(type,name))
                .map(modFileResourcePack -> getInputStreamOrThrow(type,name,modFileResourcePack))
                .findAny();
    }

    private static InputStream getInputStreamOrThrow(ResourcePackType type, ResourceLocation name, ModFileResourcePack source) {
        try {
            return source.getResourceStream(type,name);
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
