package com.alessiodp.core.bukkit.gui.items;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public abstract class MenuItem {
	public abstract ItemStack getItem();
	public abstract void performClick(InventoryClickEvent event);
}
