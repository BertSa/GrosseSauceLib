package ca.bertsa.grossesaucelib.utils;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.ItemStack;

public class ItemUtils {
    public static boolean isStackFood(ItemStack stack) {
        return stack.contains(DataComponentTypes.FOOD);
    }
}
