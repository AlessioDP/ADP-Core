package com.alessiodp.core.bukkit.gui.items;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;
import java.util.function.Supplier;

@RequiredArgsConstructor
public class DynamicItem extends MenuItem {
	@NonNull private Supplier<ItemStack> item;
	@NonNull private Consumer<InventoryClickEvent> click;
	
	@Override
	public ItemStack getItem() {
		return item.get();
	}
	
	@Override
	public void performClick(InventoryClickEvent event) {
		click.accept(event);
	}
}
