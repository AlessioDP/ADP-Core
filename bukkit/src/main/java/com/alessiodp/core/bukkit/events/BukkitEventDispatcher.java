package com.alessiodp.core.bukkit.events;

import com.alessiodp.core.common.events.EventDispatcher;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;

public class BukkitEventDispatcher implements EventDispatcher {
	@Override
	public void callEvent(Object event) {
		if (event instanceof Event) {
			Bukkit.getPluginManager().callEvent((Event) event);
		}
	}
}
