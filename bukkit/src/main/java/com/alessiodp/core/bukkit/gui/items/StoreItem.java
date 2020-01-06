package com.alessiodp.core.bukkit.gui.items;

import com.alessiodp.core.bukkit.gui.menu.Menu;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

public class StoreItem extends MenuItem {
	private ItemStack item = null;
	private final Menu menu;
	private final boolean clearOnEmptyMaterial;
	private final Consumer<ItemStack> store;
	
	public StoreItem(Menu menu, boolean clearOnEmptyMaterial, Consumer<ItemStack> store) {
		this.menu = menu;
		this.clearOnEmptyMaterial = clearOnEmptyMaterial;
		this.store = store;
	}
	
	@Override
	public ItemStack getItem() {
		return item;
	}
	
	@Override
	public void performClick(InventoryClickEvent event) {
		ItemStack cursor = event.getCursor();
		if (cursor != null
				&& (clearOnEmptyMaterial || !cursor.getType().equals(Material.AIR))) {
			item = cursor.clone();
			if (item.getAmount() > 1)
				item.setAmount(1);
			store.accept(item);
			menu.invalidateItems();
		}
		event.setCancelled(true);
	}
}
