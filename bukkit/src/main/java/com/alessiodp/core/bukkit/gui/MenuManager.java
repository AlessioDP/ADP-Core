package com.alessiodp.core.bukkit.gui;

import com.alessiodp.core.bukkit.gui.menu.Menu;
import com.alessiodp.core.common.ADPPlugin;
import lombok.Getter;
import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.UUID;

public class MenuManager {
	@NonNull protected final ADPPlugin plugin;
	@Getter private final HashMap<UUID, Menu> activeMenus;
	
	public MenuManager(ADPPlugin plugin) {
		this.plugin = plugin;
		activeMenus = new HashMap<>();
		Bukkit.getPluginManager().registerEvents(new MenuListener(this), ((Plugin) plugin.getBootstrap()));
	}
}
