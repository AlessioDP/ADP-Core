package com.alessiodp.core.bukkit.gui.menu;

import org.bukkit.entity.Player;

public interface IMenu {
	void open(Player player);
	void close(Player player);
	boolean isOpen(Player player);
	void setInvalidateItems(boolean invalidate);
	default void invalidateItems() {setInvalidateItems(true);}
}
