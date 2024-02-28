package com.topi_banana.boxstack;

import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.block.ShulkerBox;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.ItemMeta;

public class ShulkerUtils {

    public static boolean isShulkerBox(Material matelial) {
        boolean isShulkerBox = false;

        switch(matelial) {
            case SHULKER_BOX:
            case BLACK_SHULKER_BOX:
            case BLUE_SHULKER_BOX:
            case BROWN_SHULKER_BOX:
            case CYAN_SHULKER_BOX:
            case GRAY_SHULKER_BOX:
            case GREEN_SHULKER_BOX:
            case LIGHT_BLUE_SHULKER_BOX:
            case LIGHT_GRAY_SHULKER_BOX:
            case LIME_SHULKER_BOX:
            case MAGENTA_SHULKER_BOX:
            case ORANGE_SHULKER_BOX:
            case PINK_SHULKER_BOX:
            case PURPLE_SHULKER_BOX:
            case RED_SHULKER_BOX:
            case WHITE_SHULKER_BOX:
            case YELLOW_SHULKER_BOX:
                isShulkerBox = true;
                break;
            default:
                break;
        }

        return isShulkerBox;
    }

    public static boolean isEmptyShulkerBox(ItemStack itemStack) {

        ItemMeta itemMeta = itemStack.getItemMeta();

        if (itemMeta instanceof BlockStateMeta) {
            BlockStateMeta blockStateMeta = (BlockStateMeta) itemMeta;
            BlockState blockState = blockStateMeta.getBlockState();

            if (blockState instanceof ShulkerBox) {
                ShulkerBox shulkerBox = (ShulkerBox) blockState;
                Inventory inventory = shulkerBox.getInventory();
                for (ItemStack item : inventory.getContents()) {
                    if (item != null && !item.getType().isAir()) return false;
                }
            }
        }
        return true;
    }

    public static boolean canPlaceShulker(InventoryType invType) {

        boolean canPlaceShulker = false;

        switch(invType) {
            case CHEST:
            case ENDER_CHEST:
            case DROPPER:
            case DISPENSER:
            case PLAYER:
            case HOPPER:
            case CRAFTING:
                canPlaceShulker = true;
                break;
            default:
                break;
        }
        return canPlaceShulker;
    }
}
