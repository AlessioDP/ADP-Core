package com.alessiodp.core.bukkit.utils;

import com.alessiodp.core.common.utils.IColorUtils;
import org.bukkit.ChatColor;

public class BukkitColorUtils implements IColorUtils {
	@Override
	public String convertColors(String message) {
		return ChatColor.translateAlternateColorCodes('&', message);
	}
	
	@Override
	public String removeColors(String message) {
		return ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', message));
	}
}
