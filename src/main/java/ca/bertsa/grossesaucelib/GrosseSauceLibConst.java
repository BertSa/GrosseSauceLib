package ca.bertsa.grossesaucelib;

import net.fabricmc.loader.api.FabricLoader;

import java.nio.file.Path;

public class GrosseSauceLibConst {
    public static final String MOD_ID = "grossesaucelib";
    public static final String MOD_NAME = "GrosseSauceLib";

    public static final Path CONFIG_DIR = FabricLoader.getInstance().getConfigDir();

    public static final int LAST_HOTBAR_SLOT_INDEX = 8;
    public static final int PLAYER_INVENTORY_SLOT_COUNT_WITHOUT_EQUIPMENT_AND_CRAFTING_SLOTS = 36;
}
