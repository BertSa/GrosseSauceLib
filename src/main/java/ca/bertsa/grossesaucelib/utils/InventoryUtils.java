package ca.bertsa.grossesaucelib.utils;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.Hand;

import java.util.Objects;
import java.util.function.Function;

import static ca.bertsa.grossesaucelib.GrosseSauceLibConst.LAST_HOTBAR_SLOT_INDEX;
import static ca.bertsa.grossesaucelib.GrosseSauceLibConst.PLAYER_INVENTORY_SLOT_COUNT_WITHOUT_EQUIPMENT_AND_CRAFTING_SLOTS;

public class InventoryUtils {
    private static Integer lastItemSwappedSlot1;
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

        lastItemSwappedSlot1 = slot1;
        lastItemSwappedSlot2 = slot2;
    }

    public static void swapStacksBack() {
        if (Objects.isNull(lastItemSwappedSlot1)) {
            return;
        }
        if (Objects.isNull(lastItemSwappedSlot2)) {
            swapStacksWithHand(Hand.OFF_HAND, lastItemSwappedSlot1);
            return;
        }

        swapStacks(lastItemSwappedSlot1, lastItemSwappedSlot2);
    }

    public static void swapStacksWithHand(Hand hand, int itemSwappedSlot) {
        MinecraftClient client = MinecraftClient.getInstance();
        ClientPlayerEntity player = client.player;
        ClientPlayerInteractionManager interactionManager = client.interactionManager;

        if (Objects.isNull(player) || Objects.isNull(interactionManager)) {
            return;
        }
        if (Hand.OFF_HAND == hand) {
            interactionManager.clickSlot(0, itemSwappedSlot, 40, SlotActionType.SWAP, player);
            lastItemSwappedSlot1 = itemSwappedSlot;
            lastItemSwappedSlot2 = null;
            return;
        }
        int selectedSlot = getSlotIndex(player.getInventory().getSelectedSlot());

        swapStacks(selectedSlot, itemSwappedSlot);
    }

    public static void forgetLastSwappedSlots() {
        lastItemSwappedSlot1 = null;
        lastItemSwappedSlot2 = null;
    }

    public static Integer getSlotIndexOfFirstMatchingItem(Function<ItemStack, Boolean> filter) {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;

        if (Objects.isNull(player)) {
            return null;
        }

        Inventory inventory = player.getInventory();
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
