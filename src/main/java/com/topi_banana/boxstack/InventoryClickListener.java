package com.topi_banana.boxstack;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryClickListener implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void InventoryClick(InventoryClickEvent event){
        try {
            if(event.getClick() != ClickType.LEFT) return;

            ItemStack cursor = new ItemStack(event.getCursor());
            ItemStack clicked = new ItemStack(event.getCurrentItem());

            boolean cursorIsShulkerBox = ShulkerUtils.isShulkerBox(cursor.getType());
            boolean clickedIsShulkerBox = ShulkerUtils.isShulkerBox(clicked.getType());

            Player player = (Player) event.getWhoClicked();
            Inventory clickedInventory = event.getClickedInventory();
            int slotClicked = event.getSlot();

            if(
                (
                    event.getAction() == InventoryAction.NOTHING ||
                    event.getAction() == InventoryAction.PICKUP_SOME
                ) &&
                cursorIsShulkerBox &&
                clickedIsShulkerBox &&
                ShulkerUtils.isEmptyShulkerBox(cursor) &&
                ShulkerUtils.isEmptyShulkerBox(clicked)
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
                ShulkerUtils.isEmptyShulkerBox(cursor) &&
                clicked.getType() == Material.AIR &&
                ShulkerUtils.canPlaceShulker(clickedInventory.getType())
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
}
