package com.alessiodp.core.bukkit.gui.menu;

import com.alessiodp.core.bukkit.gui.MenuContent;
import com.alessiodp.core.bukkit.gui.MenuManager;
import com.alessiodp.core.bukkit.gui.MenuSlot;
import com.alessiodp.core.bukkit.gui.items.MenuItem;
import com.alessiodp.core.bukkit.gui.items.StaticItem;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class PagedMenu extends Menu {
	
	protected int currentPage = 1;
	@Getter @Setter protected ItemStack previousPage;
	@Getter @Setter protected ItemStack nextPage;
	
	@Getter protected final HashMap<Integer, MenuContent> contents;
	
	public PagedMenu(MenuManager menuManager, String title, int rows, boolean updateOnClick) {
		super(menuManager, title, rows, null, updateOnClick);
		contents = new HashMap<>();
	}
	
	@Override
	protected void populateInventory(Inventory inventory) {
		// wip null check on page items - throw a message in console
		inventory.clear();
		
		HashMap<MenuSlot, MenuItem> items = getContent().getItems();
		if (currentPage > 1) {
			items.put(new MenuSlot(0, rows - 1), new StaticItem(previousPage, event -> {
				event.setCancelled(true);
				previousPage();
			}));
		}
		if (currentPage < contents.size()) {
			items.put(new MenuSlot(lengthRow - 1, rows - 1), new StaticItem(nextPage, event -> {
				event.setCancelled(true);
				nextPage();
			}));
		}
		for (Map.Entry<MenuSlot, MenuItem> e : items.entrySet()) {
			inventory.setItem(e.getKey().calculateSlot(lengthRow), e.getValue().getItem());
		}
	}
	
	protected void nextPage() {
		currentPage++;
		invalidateItems();
	}
	
	protected void previousPage() {
		currentPage--;
		invalidateItems();
	}
	
	@Override
	public MenuContent getContent() {
		return contents.get(currentPage);
	}
}
