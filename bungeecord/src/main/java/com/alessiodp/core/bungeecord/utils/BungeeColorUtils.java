package com.alessiodp.core.bungeecord.utils;

import com.alessiodp.core.common.utils.IColorUtils;
import net.md_5.bungee.api.ChatColor;

import java.util.Locale;

public class BungeeColorUtils implements IColorUtils {
	@Override
	public boolean existsColor(String colorName) {
		try {
			ChatColor.valueOf(colorName.toUpperCase(Locale.ENGLISH));
			return true;
		} catch (Exception ignored) {}
		return false;
	}
	
	@Override
	public String convertColorByName(String colorName) {
		try {
			return ChatColor.valueOf(colorName.toUpperCase(Locale.ENGLISH)).toString();
		} catch (Exception ignored) {}
		return "";
	}
	
	@Override
	public String convertColors(String message) {
		return ChatColor.translateAlternateColorCodes('&', message);
	}
	
	@Override
	public String removeColors(String message) {
		return ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', message));
	}
}
