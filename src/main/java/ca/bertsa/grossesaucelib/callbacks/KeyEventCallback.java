package ca.bertsa.grossesaucelib.callbacks;

import net.minecraft.client.MinecraftClient;
import org.jetbrains.annotations.NotNull;

public interface KeyEventCallback {
    void handleKeyReleased(@NotNull MinecraftClient client);

    void handleKeyPressed(@NotNull MinecraftClient client);
}
