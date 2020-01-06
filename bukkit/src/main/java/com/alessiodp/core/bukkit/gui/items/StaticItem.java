package com.alessiodp.core.bukkit.gui.items;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

@RequiredArgsConstructor
public class StaticItem extends MenuItem {
	@NonNull private ItemStack item;
	@NonNull private Consumer<InventoryClickEvent> click;
	
	@Override
	public ItemStack getItem() {
		return item;
	}
	
	@Override
	public void performClick(InventoryClickEvent event) {
		click.accept(event);
	}
}