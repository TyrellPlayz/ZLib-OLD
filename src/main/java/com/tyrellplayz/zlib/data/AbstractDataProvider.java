package com.tyrellplayz.zlib.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IDataProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.file.Path;

public abstract class AbstractDataProvider implements IDataProvider {
    protected final Logger LOGGER = LogManager.getLogger("ZLib Data");
    protected final Gson GSON = (new GsonBuilder()).setPrettyPrinting().create();

    protected final DataGenerator GENERATOR;
    protected final Path OUTPUT_PATH;
    protected final Path INPUT_PATH;
    protected final String MOD_ID;

    public AbstractDataProvider(DataGenerator generator, String modId) {
        this.GENERATOR = generator;
        this.MOD_ID = modId;
        this.OUTPUT_PATH = generator.getOutputFolder();
        this.INPUT_PATH = generator.getInputFolders().stream().findFirst().orElse(null);
    }
}
