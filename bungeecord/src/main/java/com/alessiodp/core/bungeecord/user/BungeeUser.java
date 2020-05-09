package com.alessiodp.core.bungeecord.user;

import com.alessiodp.core.common.ADPPlugin;
import com.alessiodp.core.common.user.User;
import lombok.AllArgsConstructor;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.UUID;

@AllArgsConstructor
public class BungeeUser implements User {
	private final ADPPlugin plugin;
	private final CommandSender sender;
	
	@Override
	public UUID getUUID() {
		return isPlayer() ? ((ProxiedPlayer) sender).getUniqueId() : null;
	}
	
	@Override
	public boolean hasPermission(String permission) {
		return sender.hasPermission(permission);
	}
	
	@Override
	public String getDynamicPermission(String startsWith) {
		for (String perm : sender.getPermissions()) {
			if (perm.startsWith(startsWith)) {
				return perm.substring(startsWith.length());
			}
		}
		return null;
	}
	
	@Override
	public boolean isOperator() {
		return false;
	}
	
	@Override
	public boolean isPlayer() {
		return (sender instanceof ProxiedPlayer);
	}
	
	@Override
	public String getName() {
		return sender.getName();
	}
	
	@Override
	public void sendMessage(String message, boolean colorTranslation) {
		if (isPlayer() && getPlugin().getJsonHandler().isJson(message))
			getPlugin().getJsonHandler().sendMessage(sender, message);
		else
			sender.sendMessage(TextComponent.fromLegacyText(colorTranslation ? ChatColor.translateAlternateColorCodes('&', message) : message));
	}
	
	@Override
	public void chat(String messageToSend) {
		if (isPlayer())
			((ProxiedPlayer) sender).chat(messageToSend);
	}
	
	@Override
	public void playSound(String sound, double volume, double pitch) {
		// Not supported
	}
	
	@Override
	public ADPPlugin getPlugin() {
		return plugin;
	}
	
	/**
	 * Get server name of the user
	 *
	 * @return the server name
	 */
	public String getServerName() {
		if (isPlayer())
			return ((ProxiedPlayer) sender).getServer().getInfo().getName();
		return "";
	}
	
	/**
	 * Force the user to connect to the given server
	 *
	 * @param serverInfo the server info
	 */
	public void connectTo(ServerInfo serverInfo) {
		if (isPlayer())
			((ProxiedPlayer) sender).connect(serverInfo);
	}
}
