package com.alessiodp.core.bukkit.gui;

import com.alessiodp.core.bukkit.gui.menu.Menu;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;

@RequiredArgsConstructor
public class MenuListener implements Listener {
	@NonNull private MenuManager menuManager;
	
	@EventHandler(ignoreCancelled = true)
	public void onInventoryDrag(InventoryDragEvent event) {
		// Disable drag
		if (event.getWhoClicked() instanceof Player) {
			Menu menu = menuManager.getActiveMenus().get(event.getWhoClicked().getUniqueId());
			if (menu != null) {
				for (int slot : event.getRawSlots()) {
					if (slot < event.getInventory().getSize()) {
						// Cancel if dragging on top inventory
						event.setCancelled(true);
						break;
					}
				}
			}
		}
	}
	
	@EventHandler(ignoreCancelled = true)
	public void onInventoryClick(InventoryClickEvent event) {
		if (event.getWhoClicked() instanceof Player) {
			Menu menu = menuManager.getActiveMenus().get(event.getWhoClicked().getUniqueId());
			if (menu != null) {
				menu.onClick(event);
			}
		}
	}
	
	@EventHandler(ignoreCancelled = true)
	public void onInventoryClose(InventoryCloseEvent event) {
		Menu menu = menuManager.getActiveMenus().get(event.getPlayer().getUniqueId());
		if (menu != null) {
			menu.onClose(event);
		}
	}
}
