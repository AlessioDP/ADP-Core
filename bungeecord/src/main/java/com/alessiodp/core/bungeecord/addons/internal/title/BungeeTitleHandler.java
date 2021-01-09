package com.alessiodp.core.bungeecord.addons.internal.title;

import com.alessiodp.core.common.addons.internal.TitleHandler;
import com.alessiodp.core.common.utils.Color;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.chat.ComponentSerializer;

public class BungeeTitleHandler extends TitleHandler {
	@Override
	public boolean sendTitle(Object user, String titleMessage, int fadeInTime, int showTime, int fadeOutTime) {
		return sendRawTitle(user, new ComponentBuilder(titleMessage).create(), fadeInTime, showTime, fadeOutTime);
	}
	
	@Override
	public boolean sendTitleJson(Object user, String titleMessage, int fadeInTime, int showTime, int fadeOutTime) {
		return sendRawTitle(user, ComponentSerializer.parse(Color.translateAlternateColorCodes(titleMessage)), fadeInTime, showTime, fadeOutTime);
	}
	
	private boolean sendRawTitle(Object user, BaseComponent[] baseComponent, int fadeInTime, int showTime, int fadeOutTime) {
		ProxiedPlayer player = (ProxiedPlayer) user;
		try {
			ProxyServer.getInstance().createTitle()
					.title(baseComponent)
					.fadeIn(fadeInTime / 50)
					.stay(showTime / 50)
					.fadeOut(fadeOutTime / 50)
					.send(player);
			return true;
		} catch (Exception ignored) {}
		return false;
	}
}
