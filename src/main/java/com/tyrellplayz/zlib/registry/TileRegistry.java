package com.tyrellplayz.zlib.registry;

import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.types.Type;
import com.tyrellplayz.zlib.ZLib;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.datafix.DataFixesManager;
import net.minecraft.util.datafix.TypeReferences;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * Contains methods to register TileEntities.
 */
public abstract class TileRegistry {

    private static final Logger LOGGER = LogManager.getLogger(ZLib.MOD_NAME+" Tile Registry");
    public static final int DATAFIXER_VERSION = 0;

    protected static final List<TileEntityType<?>> TILES = new ArrayList<>();

    /**
     * Creates and Registers a new {@link TileEntityType}.
     * @param id The id of the {@link TileEntity}.
     * @param factoryIn A new instance of the TileEntities class (Eg MyTile::new).
     * @param validBlocks Blocks that are linked to this tile.
     * @param <T> The class of the {@link TileEntity}.
     * @return The created {@link TileEntityType}.
     */
    protected static <T extends TileEntity> TileEntityType<T> register(String id, Supplier<T> factoryIn, Block... validBlocks) {
        return register(new ResourceLocation(id),factoryIn,validBlocks);
    }

    /**
     * Creates and Registers a new {@link TileEntityType}.
     * @param id The id of the {@link TileEntity}.
     * @param factoryIn A new instance of the {@link TileEntity}s class (Eg MyTile::new).
     * @param validBlocks Blocks that are linked to this tile.
     * @param <T> The class of the {@link TileEntity}.
     * @return The created {@link TileEntityType}.
     */
    protected static <T extends TileEntity> TileEntityType<T> register(ResourceLocation id, Supplier<T> factoryIn, Block... validBlocks){
        Validate.notNull(id,"Tile should have a registry name");
        Validate.notNull(factoryIn,"Tile factory is null for "+id.toString());
        TileEntityType.Builder<T> builder = TileEntityType.Builder.create(factoryIn,validBlocks);
        TileEntityType<T> tileEntityType = builder.build(getDataFixer(id));
        tileEntityType.setRegistryName(id);
        TILES.add(tileEntityType);
        return tileEntityType;
    }

    private static Type<?> getDataFixer(ResourceLocation id) {
        try{
            return DataFixesManager.getDataFixer()
                    .getSchema(DataFixUtils.makeKey(DATAFIXER_VERSION))
                    .getChoiceType(TypeReferences.BLOCK_ENTITY, id.toString());
        }catch (IllegalArgumentException e){
            if(SharedConstants.developmentMode) throw e;
            LOGGER.warn("No data fixer registered for block entity {}",id);
            return null;
        }
    }

    @SubscribeEvent
    public static void registerTiles(final RegistryEvent.Register<TileEntityType<?>> event) {
        TILES.forEach(type -> event.getRegistry().register(type));
    }

}
