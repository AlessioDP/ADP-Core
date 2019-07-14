package com.alessiodp.core.bungeecord.events;

import com.alessiodp.core.common.ADPPlugin;
import com.alessiodp.core.common.events.EventDispatcher;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.md_5.bungee.api.plugin.Event;
import net.md_5.bungee.api.plugin.Plugin;

@RequiredArgsConstructor
public class BungeeEventDispatcher implements EventDispatcher {
	@NonNull private final ADPPlugin plugin;
	
	@Override
	public void callEvent(Object event) {
		if (event instanceof Event) {
			((Plugin) plugin.getBootstrap()).getProxy().getPluginManager().callEvent((Event) event);
		}
	}
}
