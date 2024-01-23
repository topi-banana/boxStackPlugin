package com.topi_banana.boxstack;

import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.block.ShulkerBox;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.function.Supplier;

import static com.topi_banana.boxstack.BoxStack.*;

public class InventoryClickListener implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void InventoryClick(InventoryClickEvent event){
        try {
            if(event.getClick() != ClickType.LEFT) return;

            ItemStack cursor = new ItemStack(event.getCursor());
            ItemStack clicked = new ItemStack(event.getCurrentItem());

            Boolean cursorIsShulkerBox = isShulkerBox(cursor.getType());
            Boolean clickedIsShulkerBox = isShulkerBox(clicked.getType());

            if( cursorIsShulkerBox && !isEmptyShulkerBox(cursor) ){
                return;
            }
            if( clickedIsShulkerBox && !isEmptyShulkerBox(clicked) ){
                return;
            }

            Player player = (Player) event.getWhoClicked();
            Inventory clickedInventory = event.getClickedInventory();
            int slotClicked = event.getSlot();

            if(
                event.getAction() == InventoryAction.NOTHING &&
                cursorIsShulkerBox &&
                clickedIsShulkerBox
            ) {
                int count = cursor.getAmount() + clicked.getAmount();
                if(count > 64) {
                    player.setItemOnCursor(new ItemStack(clicked.getType(), count - 64));
                    count = 64;
                } else {
                    player.setItemOnCursor(null);
                }
                if (slotClicked >= 0 && slotClicked < clickedInventory.getSize()) {
                    clickedInventory.setItem(slotClicked, new ItemStack(clicked.getType(), count));
                }
                event.setCancelled(true);
            } else if(
                event.getAction() == InventoryAction.PLACE_ALL &&
                cursorIsShulkerBox &&
                clicked.getType() == Material.AIR
            ) {
                int count = cursor.getAmount();
                if(count > 64) {
                    player.setItemOnCursor(new ItemStack(cursor.getType(), count - 64));
                    count = 64;
                } else {
                    player.setItemOnCursor(null);
                }
                if (slotClicked >= 0 && slotClicked < clickedInventory.getSize()) {
                    clickedInventory.setItem(slotClicked, new ItemStack(cursor.getType(), count));
                }
                event.setCancelled(true);
            }
        } catch ( IllegalArgumentException e ){
            // pass
            // crafter系、sort系 mod機能使うとExeption吐く
        }
    }

    private boolean isShulkerBox(Material mat) {
        boolean isShulkerBox = false;

        switch(mat) {
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
    private boolean isEmptyShulkerBox(ItemStack itemStack) {

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
}
