package com.alessiodp.core.bungeecord.addons.internal.json;

import com.alessiodp.core.common.addons.internal.JsonHandler;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.chat.ComponentSerializer;

public class BungeeJsonHandler implements JsonHandler {
	private final JsonParser jsonParser;
	
	public BungeeJsonHandler() {
		jsonParser = new JsonParser();
	}
	
	public boolean sendMessage(Object user, String jsonMessage) {
		boolean ret = false;
		ProxiedPlayer player = (ProxiedPlayer) user;
		try {
			player.sendMessage(ComponentSerializer.parse(jsonMessage));
			ret = true;
		} catch (Exception ignored) {
		}
		return ret;
	}
	
	public boolean isJson(String jsonMessage) {
		boolean ret = false;
		try {
			jsonParser.parse(jsonMessage);
			ret = true;
		} catch (JsonParseException ignored) {}
		return ret;
	}
}
