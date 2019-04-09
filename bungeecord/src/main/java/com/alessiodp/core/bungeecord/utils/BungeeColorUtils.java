package com.alessiodp.core.bungeecord.utils;

import com.alessiodp.core.common.utils.IColorUtils;
import net.md_5.bungee.api.ChatColor;

public class BungeeColorUtils implements IColorUtils {
	@Override
	public String convertColors(String message) {
		return ChatColor.translateAlternateColorCodes('&', message);
	}
	
	@Override
	public String removeColors(String message) {
		return ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', message));
	}
}
