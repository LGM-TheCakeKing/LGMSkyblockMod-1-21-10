package ml.thecakeking.lgmmods.chattrigger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ml.thecakeking.lgmmods.LGMSkyblockMod;
import ml.thecakeking.lgmmods.Model.ChatTrigger;
import net.fabricmc.loader.api.FabricLoader;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TriggerConfigHandler {
    private static final Path CONFIG_FILE = FabricLoader.getInstance().getConfigDir().resolve("chattriggers.json");
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static void save() {
        try (Writer writer = new FileWriter(CONFIG_FILE.toFile())) {
            GSON.toJson(TriggerManager.triggers, writer);
        } catch (IOException e) {
            LGMSkyblockMod.LOGGER.error("Error while saving config file!", e);
             e.printStackTrace();
        }
    }

    public static void load() {
        if (!CONFIG_FILE.toFile().exists()) return;
        try (Reader reader = new FileReader(CONFIG_FILE.toFile())) {
            ChatTrigger[] loaded = GSON.fromJson(reader, ChatTrigger[].class);
            if (loaded != null) {
                TriggerManager.triggers.clear();
                TriggerManager.triggers.addAll(Arrays.asList(loaded));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
