package ca.bertsa.grossesaucelib.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static ca.bertsa.grossesaucelib.GrosseSauceLibConst.CONFIG_DIR;
import static net.fabricmc.fabric.impl.resource.loader.ModResourcePackUtil.GSON;

public class FileUtils {
    public static <T> T readFile(String modId, String fileName, Class<T> clazz) {
        Path modDir = CONFIG_DIR.resolve(modId);
        Path file = modDir.resolve(fileName);

        if (!Files.exists(modDir) || !Files.exists(file)) {
            return null;
        }

        try {
            String json = new String(Files.readAllBytes(file));
            return GSON.fromJson(json, clazz);
        } catch (IOException ignored) {
            return null;
        }
    }

    public static void writeFile(String modId, String fileName, Object content) {
        Path modDir = CONFIG_DIR.resolve(modId);
        Path file = modDir.resolve(fileName);

        try {
            if (!Files.exists(modDir)) {
                Files.createDirectories(modDir);
            }
            Files.write(file, GSON.toJson(content).getBytes());
        } catch (IOException ignored) {

        }

    }
}
