package ca.bertsa.grossesaucelib.utils;

import net.minecraft.client.MinecraftClient;

public class PlayerInteractionUtils {
    public static void setUseKeyPressedTrue() {
        setUseKeyPressed(true);
    }

    public static void setUseKeyPressedFalse() {
        setUseKeyPressed(false);
    }

    public static void setUseKeyPressed(boolean pressed) {
        MinecraftClient.getInstance().options.useKey.setPressed(pressed);
    }
}
