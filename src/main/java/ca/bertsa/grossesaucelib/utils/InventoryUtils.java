package ca.bertsa.grossesaucelib.utils;

import lombok.AccessLevel;
import lombok.Setter;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.Hand;

import java.util.Objects;
import java.util.function.Function;

import static ca.bertsa.grossesaucelib.LibConstants.LAST_HOTBAR_SLOT_INDEX;
import static ca.bertsa.grossesaucelib.LibConstants.PLAYER_INVENTORY_SLOT_COUNT_WITHOUT_EQUIPMENT_AND_CRAFTING_SLOTS;

public class InventoryUtils {

    @Setter(AccessLevel.PRIVATE)
    private static Integer lastItemSwappedSlot1;
    @Setter(AccessLevel.PRIVATE)
    private static Integer lastItemSwappedSlot2;


    public static void swapStacks(int slot1, int slot2) {
        MinecraftClient client = MinecraftClient.getInstance();
        ClientPlayerEntity player = client.player;
        ClientPlayerInteractionManager interactionManager = client.interactionManager;
        if (player == null || interactionManager == null) {
            return;
        }

        if (slot1 == PLAYER_INVENTORY_SLOT_COUNT_WITHOUT_EQUIPMENT_AND_CRAFTING_SLOTS) {
            interactionManager.clickSlot(0, slot1, 0, SlotActionType.SWAP, player);
            interactionManager.clickSlot(0, slot2, 0, SlotActionType.SWAP, player);
            interactionManager.clickSlot(0, slot1, 0, SlotActionType.SWAP, player);
        } else {
            interactionManager.clickSlot(0, slot2, 0, SlotActionType.SWAP, player);
            interactionManager.clickSlot(0, slot1, 0, SlotActionType.SWAP, player);
            interactionManager.clickSlot(0, slot2, 0, SlotActionType.SWAP, player);
        }

        setLastItemSwappedSlot1(slot1);
        setLastItemSwappedSlot2(slot2);
    }

    public static void swapStacksBack() {
        if (lastItemSwappedSlot1 == null || lastItemSwappedSlot2 == null) {
            return;
        }

        swapStacks(lastItemSwappedSlot1, lastItemSwappedSlot2);
    }

    public static void swapStacksToPreferredHand(Hand preferredHand, int itemSwappedSlot) {
        MinecraftClient client = MinecraftClient.getInstance();
        ClientPlayerEntity player = client.player;
        ClientPlayerInteractionManager interactionManager = client.interactionManager;

        if (player == null || interactionManager == null) {
            return;
        }

        if (preferredHand == Hand.OFF_HAND) {
            interactionManager.clickSlot(0, itemSwappedSlot, 40, SlotActionType.SWAP, player);
            setLastItemSwappedSlot1(itemSwappedSlot);
            return;
        }
        int selectedSlot = getSlotIndex(player.getInventory().selectedSlot);

        swapStacks(selectedSlot, itemSwappedSlot);
    }

    public static Integer getSlotIndexOfFirstMatchingItem(Function<ItemStack, Boolean> filter) {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;

        if (player == null) {
            return null;
        }
        Inventory inventory = Objects.requireNonNull(player).getInventory();
        for (int slot = 0; slot < inventory.size(); slot++) {
            final ItemStack itemStack = inventory.getStack(slot);
            if (filter.apply(itemStack)) {
                return getSlotIndex(slot);
            }
        }
        return null;
    }

    private static int getSlotIndex(int slot) {
        if (slot <= LAST_HOTBAR_SLOT_INDEX) {
            slot += PLAYER_INVENTORY_SLOT_COUNT_WITHOUT_EQUIPMENT_AND_CRAFTING_SLOTS;
        }

        return slot;
    }
}
