package com.tyrellplayz.zlib.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IDataProvider;
import net.minecraft.resources.ResourcePackType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;
import java.io.File;
import java.nio.file.Path;

public abstract class AbstractDataProvider implements IDataProvider {
    protected final Logger LOGGER;
    protected final Gson GSON = (new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping().create();

    protected final DataGenerator GENERATOR;
    private final Path OUTPUT_PATH;
    protected final Path INPUT_PATH;
    protected final String MOD_ID;
    protected final DataProviderType PROVIDER_TYPE;

    public AbstractDataProvider(DataGenerator generator, String modId, DataProviderType providerType) {
        this.LOGGER = LogManager.getLogger(modId);
        this.GENERATOR = generator;
        this.MOD_ID = modId;
        this.OUTPUT_PATH = generator.getOutputFolder();
        this.INPUT_PATH = generator.getInputFolders().stream().findFirst().orElse(null);
        this.PROVIDER_TYPE = providerType;
    }

    public Path getOutputPath() {
        return OUTPUT_PATH.resolve(PROVIDER_TYPE.getPackType().getDirectoryName()+"/"+MOD_ID+"/"+PROVIDER_TYPE.getDirectoryName());
    }

    public Path getInputPath() {
        return INPUT_PATH.resolve(PROVIDER_TYPE.getPackType().getDirectoryName()+"/"+MOD_ID+"/"+PROVIDER_TYPE.getDirectoryName());
    }

}
