package com.alessiodp.core.bukkit.addons.internal.json;

import com.alessiodp.core.common.addons.internal.JsonHandler;
import com.alessiodp.core.common.utils.Color;
import net.md_5.bungee.chat.ComponentSerializer;
import org.bukkit.entity.Player;

/**
 * Spigot handler for JSON handling
 *
 * Better this than Bukkit due to not using reflections.
 */
public class SpigotJsonHandler extends JsonHandler {
	
	@Override
	public boolean sendMessage(Object user, String jsonMessage) {
		boolean ret = false;
		Player player = (Player) user;
		if (player != null) {
			try {
				player.spigot().sendMessage(ComponentSerializer.parse(Color.translateAlternateColorCodes(jsonMessage)));
				ret = true;
			} catch (Exception ignored) {}
		}
		return ret;
	}
}
