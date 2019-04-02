package com.alessiodp.core.bukkit.addons.internal.json;

import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import net.md_5.bungee.chat.ComponentSerializer;
import org.bukkit.entity.Player;

/**
 * Spigot handler for JSON handling
 *
 * Better this than Bukkit due to not using reflections.
 */
public class SpigotJsonHandler implements JsonHandler {
	private final JsonParser jsonParser;
	
	public SpigotJsonHandler() {
		jsonParser = new JsonParser();
	}
	
	@Override
	public boolean sendMessage(Player player, String jsonMessage) {
		boolean ret = false;
		try {
			player.spigot().sendMessage(ComponentSerializer.parse(jsonMessage));
			ret = true;
		} catch (Exception ignored) {
		}
		return ret;
	}
	
	@Override
	public boolean isJson(String jsonMessage) {
		boolean ret = false;
		try {
			jsonParser.parse(jsonMessage);
			ret = true;
		} catch (JsonParseException ignored) {}
		return ret;
	}
}
