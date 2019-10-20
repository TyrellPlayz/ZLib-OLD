package com.tyrellplayz.zlib.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IDataProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;
import java.io.File;
import java.nio.file.Path;

public abstract class AbstractDataProvider implements IDataProvider {
    protected final Logger LOGGER;
    protected final Gson GSON = (new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping().create();

    protected final DataGenerator GENERATOR;
    protected final Path OUTPUT_PATH;
    protected final Path INPUT_PATH;
    protected final String MOD_ID;

    public AbstractDataProvider(DataGenerator generator, String modId) {
        this.LOGGER = LogManager.getLogger(modId);
        this.GENERATOR = generator;
        this.MOD_ID = modId;
        this.OUTPUT_PATH = generator.getOutputFolder();
        this.INPUT_PATH = generator.getInputFolders().stream().findFirst().orElse(null);
    }

    @Nullable
    protected File getInputBlockStateFolder(String modId) {
        if(INPUT_PATH == null) return null;
        return INPUT_PATH.resolve("assets/"+modId+"/blockstates").toFile();
    }

    protected File getOutputBlockStateFolder(String modId) {
        return OUTPUT_PATH.resolve("assets/"+modId+"/blockstates").toFile();
    }

    @Nullable
    protected File getInputBlockModelFolder(String modId) {
        if(INPUT_PATH == null) return null;
        return INPUT_PATH.resolve("assets/"+modId+"/models/block").toFile();
    }

    protected File getOutputBlockModelFolder(String modId) {
        return OUTPUT_PATH.resolve("assets/"+modId+"/models/block").toFile();
    }

    @Nullable
    protected File getInputItemModelFolder(String modId) {
        if(INPUT_PATH == null) return null;
        return INPUT_PATH.resolve("assets/"+modId+"/models/item").toFile();
    }

    protected File getOutputItemModelFolder(String modId) {
        return OUTPUT_PATH.resolve("assets/"+modId+"/models/item").toFile();
    }

}
