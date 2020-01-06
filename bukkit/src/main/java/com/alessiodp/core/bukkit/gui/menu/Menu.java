package com.alessiodp.core.bukkit.gui.menu;

import com.alessiodp.core.bukkit.gui.MenuContent;
import com.alessiodp.core.bukkit.gui.MenuManager;
import com.alessiodp.core.bukkit.gui.MenuSlot;
import com.alessiodp.core.bukkit.gui.items.MenuItem;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.HashMap;
import java.util.Map;

public class Menu implements IMenu {
	protected MenuManager menuManager;
	
	protected String title;
	protected int rows;
	protected int lengthRow;
	protected InventoryType inventoryType;
	@Getter @Setter protected boolean updateOnClick;
	
	@Getter protected MenuContent content;
	@Setter public boolean invalidateItems;
	
	public Menu(MenuManager menuManager, String title, int rows, boolean updateOnClick) {
		this(menuManager, title, rows, null, updateOnClick);
	}
	
	public Menu(MenuManager menuManager, String title, InventoryType inventoryType, boolean updateOnClick) {
		this(menuManager, title, 0, inventoryType, updateOnClick);
	}
	
	protected Menu(@NonNull MenuManager menuManager, @NonNull String title, int rows, @Nullable InventoryType inventoryType, boolean updateOnClick) {
		if (inventoryType == null && !(rows >= 1 && rows <= 6)) {
			throw new IllegalArgumentException("Rows must be between 1 and 6, it was " + rows);
		}
		this.menuManager = menuManager;
		
		this.title = title;
		this.rows = rows;
		this.inventoryType = inventoryType;
		this.updateOnClick = updateOnClick;
		if (inventoryType != null) {
			content = new MenuContent(inventoryType);
			lengthRow = inventoryType.getDefaultSize();
		} else {
			content = new MenuContent(9, rows);
			lengthRow = 9;
		}
		
		invalidateItems = false;
	}
	
	protected Inventory makeInventory(Player player) {
		return inventoryType != null ? Bukkit.createInventory(player, inventoryType, title) : Bukkit.createInventory(player, rows * 9, title);
	}
	
	protected void populateInventory(Inventory inventory) {
		inventory.clear();
		
		HashMap<MenuSlot, MenuItem> items = getContent().getItems();
		for (Map.Entry<MenuSlot, MenuItem> e : items.entrySet()) {
			try {
				inventory.setItem(e.getKey().calculateSlot(lengthRow), e.getValue().getItem());
			} catch (IndexOutOfBoundsException ex) {
				throw new IndexOutOfBoundsException("Tried to insert item " + e.getKey().getX() + "," + e.getKey().getY() + " as number " + e.getKey().calculateSlot(lengthRow));
			}
		}
	}
	
	public void open(Player player) {
		Inventory inventory = makeInventory(player);
		populateInventory(inventory);
		
		menuManager.getActiveMenus().put(player.getUniqueId(), this);
		player.openInventory(inventory);
	}
	
	public void close(Player player) {
		if (isOpen(player)) {
			player.closeInventory();
			menuManager.getActiveMenus().remove(player.getUniqueId());
		}
	}
	
	public void update(Player player) {
		if (isOpen(player)) {
			populateInventory(player.getOpenInventory().getTopInventory());
		}
	}
	
	public boolean isOpen(Player player) {
		return this.equals(menuManager.getActiveMenus().get(player.getUniqueId()));
	}
	
	public void onClose(InventoryCloseEvent event) {
		menuManager.getActiveMenus().remove(event.getPlayer().getUniqueId());
	}
	
	public void onClick(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		if (event.getAction() == InventoryAction.COLLECT_TO_CURSOR
				|| event.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY
				|| event.getAction() == InventoryAction.NOTHING) {
			// Invalid action
			event.setCancelled(true);
			return;
		}
		
		// Check same instance and only top inventory
		if (event.getClickedInventory() == player.getOpenInventory().getTopInventory()) {
			MenuItem item = getContent().get(MenuSlot.makeMenuSlot(event.getSlot(), lengthRow));
			
			if (item != null) {
				item.performClick(event);
				
				if (updateOnClick || invalidateItems) {
					update(player);
					setInvalidateItems(false);
				}
			} else {
				// Prevent put items
				if (event.getCursor() != null) {
					event.setCancelled(true);
				}
			}
		}
	}
}
