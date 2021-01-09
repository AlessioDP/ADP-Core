package com.alessiodp.core.common.bootstrap;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum PluginPlatform {
	BUKKIT("Bukkit"),
	BUNGEECORD("BungeeCord");
	
	@Getter
	private final String name;
}
