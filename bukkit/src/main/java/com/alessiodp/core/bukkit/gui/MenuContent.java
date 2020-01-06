package com.alessiodp.core.bukkit.gui;

import com.alessiodp.core.bukkit.gui.items.MenuItem;
import org.bukkit.event.inventory.InventoryType;

import java.util.HashMap;

public class MenuContent {
	private final HashMap<MenuSlot, MenuItem> items;
	private final int limitX;
	private final int limitY;
	
	public MenuContent(int columns, int rows) {
		items = new HashMap<>();
		limitX = columns;
		limitY = rows;
	}
	
	public MenuContent(InventoryType inventoryType) {
		items = new HashMap<>();
		limitX = 1;
		limitY = inventoryType.getDefaultSize();
	}
	
	public HashMap<MenuSlot, MenuItem> getItems() {
		return items;
	}
	
	public MenuItem get(int x, int y) {
		return get(new MenuSlot(x, y));
	}
	
	public MenuItem get(MenuSlot slot) {
		return items.get(slot);
	}
	
	public MenuContent set(int x, int y, MenuItem item) {
		unset(x, y);
		items.put(new MenuSlot(x, y), item);
		return this;
	}
	
	public MenuContent unset(int x, int y) {
		items.remove(new MenuSlot(x, y));
		return this;
	}
	
	public MenuContent fill(MenuItem item) {
		for (int x=0; x < limitX; x++) {
			for (int y=0; y < limitY; y++) {
				items.put(new MenuSlot(x, y), item);
			}
		}
		return this;
	}
}
