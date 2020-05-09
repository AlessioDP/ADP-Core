package com.alessiodp.core.bungeecord.user;

import com.alessiodp.core.common.ADPPlugin;
import com.alessiodp.core.common.user.OfflineUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.UUID;

@AllArgsConstructor
public class BungeeOfflineUser implements OfflineUser {
	@Getter private final ADPPlugin plugin;
	private final ProxiedPlayer player;
	private final UUID uuid;
	
	@Override
	public UUID getUUID() {
		return uuid;
	}
	
	@Override
	public boolean isOperator() {
		return false;
	}
	
	@Override
	public boolean isOnline() {
		return player != null;
	}
	
	@Override
	public String getName() {
		return player != null ? player.getName() : "";
	}
}