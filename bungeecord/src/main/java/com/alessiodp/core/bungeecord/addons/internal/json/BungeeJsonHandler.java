package com.alessiodp.core.bungeecord.addons.internal.json;

import com.alessiodp.core.common.addons.internal.JsonHandler;
import com.alessiodp.core.common.utils.Color;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.chat.ComponentSerializer;

public class BungeeJsonHandler extends JsonHandler {
	
	@Override
	public boolean sendMessage(Object user, String jsonMessage) {
		boolean ret = false;
		ProxiedPlayer player = (ProxiedPlayer) user;
		try {
			player.sendMessage(ComponentSerializer.parse(Color.translateAlternateColorCodes(jsonMessage)));
			ret = true;
		} catch (Exception ignored) {
		}
		return ret;
	}
}
